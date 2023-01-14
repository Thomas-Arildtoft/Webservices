package dk.dtu.pay.paymentmanagement;

import com.rabbitmq.client.Channel;
import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.InitiatePaymentDTO;
import dk.dtu.pay.utils.models.User;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

public class Service {

    private final MessageQueue messageQueue;
    private BankService bankService = new BankServiceService().getBankServicePort();

    public Service(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
        subscribePaymentInitiate();
    }

    private void subscribePaymentInitiate() {
        messageQueue.addHandler(QueueNames.INITIATE_PAYMENT_REQUESTED,
                (event) -> {
                    InitiatePaymentDTO initiatePaymentDTO = event.getArgument(0, InitiatePaymentDTO.class);
                    getMerchantAccountId(initiatePaymentDTO);
                });
    }

    @SneakyThrows
    private void getMerchantAccountId(InitiatePaymentDTO initiatePaymentDTO) {
        messageQueue.publish(QueueNames.PAYMENT_MANAGEMENT_ACCOUNT_REQUESTED, new Event(new Object[]{initiatePaymentDTO.getMerchantUser()}));
        CompletableFuture<AccountId> merchantAccountIdFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(QueueNames.PAYMENT_MANAGEMENT_ACCOUNT_RETURNED,
                (event) -> {
                    merchantAccountIdFuture.complete(event.getArgument(0, AccountId.class));
                });
        AccountId merchantAccountId = merchantAccountIdFuture.join();
        if (channel != null)
            channel.close();
        getCustomerUser(initiatePaymentDTO, merchantAccountId);
    }

    @SneakyThrows
    private void getCustomerUser(InitiatePaymentDTO initiatePaymentDTO, AccountId merchantAccountId) {
        messageQueue.publish(QueueNames.USER_FROM_TOKEN_REQUESTED, new Event(new Object[]{initiatePaymentDTO.getCustomerToken()}));
        CompletableFuture<User> customerUserFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(QueueNames.USER_FROM_TOKEN_RETURNED,
                (event) -> {
                    customerUserFuture.complete(event.getArgument(0, User.class));
                });
        User customerUser = customerUserFuture.join();
        if (channel != null)
            channel.close();
        getCustomerAccountId(initiatePaymentDTO, merchantAccountId, customerUser);
    }

    @SneakyThrows
    private void getCustomerAccountId(InitiatePaymentDTO initiatePaymentDTO, AccountId merchantAccountId, User customerUser) {
        messageQueue.publish(QueueNames.PAYMENT_MANAGEMENT_ACCOUNT_REQUESTED, new Event(new Object[]{customerUser}));
        CompletableFuture<AccountId> customerAccountIdFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(QueueNames.PAYMENT_MANAGEMENT_ACCOUNT_RETURNED,
                (event) -> {
                    customerAccountIdFuture.complete(event.getArgument(0, AccountId.class));
                });
        AccountId customerAccountId = customerAccountIdFuture.join();
        if (channel != null)
            channel.close();
        realisePayment(customerAccountId, merchantAccountId, initiatePaymentDTO.getAmount());
    }

    private void realisePayment(AccountId customer, AccountId merchant, BigDecimal amount) {
        try {
            bankService.transferMoneyFromTo(
                    customer.getId(),
                    merchant.getId(),
                    amount,
                    "Payment please");
            messageQueue.publish(QueueNames.INITIATE_PAYMENT_RETURNED, new Event(new Object[]{"Success"}));
        } catch (BankServiceException_Exception e) {
            messageQueue.publish(QueueNames.INITIATE_PAYMENT_RETURNED, new Event(new Object[]{"Failure"}));
        }
    }

}
