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
        addInitiatePaymentRequestedSubscriber();
    }

    /* WORKFLOW
    * 1. Listen for INITIATE_PAYMENT_REQUESTED
    * 2. Request accountManagement for merchantAccountId - requestMerchantAccountId(initiatePaymentDTO)
    * 3. Request tokenManagement to retrieve customer User out of the token - requestCustomerUser(InitiatePaymentDTO initiatePaymentDTO, AccountId merchantAccountId)
    * 4. Request accountManagement for customerAccountId - requestMerchantAccountId(InitiatePaymentDTO initiatePaymentDTO, AccountId merchantAccountId, User customerUser)
    * 5. Having all necessary info realise payment - realisePayment(AccountId customer, AccountId merchant, BigDecimal amount)
    * 6. Return message to INITIATE_PAYMENT_RETURNED
    * */
    private void addInitiatePaymentRequestedSubscriber() {
        messageQueue.addHandler(QueueNames.INITIATE_PAYMENT_REQUESTED,
                (event) -> {
                    InitiatePaymentDTO initiatePaymentDTO = event.getArgument(0, InitiatePaymentDTO.class);
                    requestMerchantAccountId(initiatePaymentDTO);
                });
    }

    @SneakyThrows
    private void requestMerchantAccountId(InitiatePaymentDTO initiatePaymentDTO) {
        messageQueue.publish(QueueNames.ACCOUNT_REQUESTED, new Event(new Object[]{initiatePaymentDTO.getMerchantUser()}));

        CompletableFuture<Event> completableFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(
                QueueNames.ACCOUNT_RETURNED,
                completableFuture::complete);

        Event event = completableFuture.join();
        AccountId accountId = event.getArgument(0, AccountId.class);

        if (channel != null)
            channel.close();
        if (accountId != null)
            requestCustomerUser(initiatePaymentDTO, accountId);
        else
            messageQueue.publish(QueueNames.INITIATE_PAYMENT_RETURNED, new Event(
                    new Object[]{"Merchant with id " + initiatePaymentDTO.getMerchantUser().getId() + " does not exist"}));

    }

    @SneakyThrows
    private void requestCustomerUser(InitiatePaymentDTO initiatePaymentDTO, AccountId merchantAccountId) {
        messageQueue.publish(QueueNames.USER_FROM_TOKEN_REQUESTED, new Event(new Object[]{initiatePaymentDTO.getCustomerToken()}));

        CompletableFuture<Event> completableFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(
                QueueNames.USER_FROM_TOKEN_RETURNED,
                completableFuture::complete);

        Event event = completableFuture.join();
        User user = event.getArgument(0, User.class);
        String message = event.getArgument(1, String.class);

        if (channel != null)
            channel.close();
        if (user != null)
            requestCustomerAccountId(initiatePaymentDTO, merchantAccountId, user);
        else
            messageQueue.publish(QueueNames.INITIATE_PAYMENT_RETURNED, new Event(new Object[]{message}));
    }

    @SneakyThrows
    private void requestCustomerAccountId(InitiatePaymentDTO initiatePaymentDTO, AccountId merchantAccountId, User customerUser) {
        messageQueue.publish(QueueNames.ACCOUNT_REQUESTED, new Event(new Object[]{customerUser}));

        CompletableFuture<Event> completableFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(
                QueueNames.ACCOUNT_RETURNED,
                completableFuture::complete);

        Event event = completableFuture.join();
        AccountId accountId = event.getArgument(0, AccountId.class);

        if (channel != null)
            channel.close();
        if (accountId != null)
            realisePayment(accountId, merchantAccountId, initiatePaymentDTO.getAmount());
        else
            messageQueue.publish(QueueNames.INITIATE_PAYMENT_RETURNED, new Event(
                    new Object[]{"Customer with id " + customerUser.getId() + " does not exist"}));
    }

    private void realisePayment(AccountId customer, AccountId merchant, BigDecimal amount) {
        try {
            bankService.transferMoneyFromTo(
                    customer.getId(),
                    merchant.getId(),
                    amount,
                    "Payment please");
            messageQueue.publish(QueueNames.INITIATE_PAYMENT_RETURNED, new Event(new Object[]{"Payment success"}));
        } catch (BankServiceException_Exception e) {
            messageQueue.publish(QueueNames.INITIATE_PAYMENT_RETURNED, new Event(new Object[]{"Payment failed"}));
        }
    }

}
