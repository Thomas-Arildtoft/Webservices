package dk.dtu.pay.merchant;

import com.rabbitmq.client.Channel;
import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.InitiatePaymentDTO;
import dk.dtu.pay.utils.models.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Service {

    private final MessageQueue messageQueue;
    private User user = null;

    public User register(AccountId accountId) {
        publishRegisterMerchantRequested(accountId);
        return addRegisterMerchantReturnedSubscriber();
    }

    public String initiatePayment(InitiatePaymentDTO initiatePaymentDTO) {
        publishInitiatePaymentRequest(initiatePaymentDTO);
        return addInitiatePaymentReturnedSubscriber();
    }

    public void clean() {
        user = null;
        messageQueue.publish(QueueNames.CLEAN_ACCOUNT_MANAGEMENT_REQUESTED, new Event(null));
        messageQueue.publish(QueueNames.CLEAN_TOKEN_MANAGEMENT_REQUESTED, new Event(null));
    }

    private void publishRegisterMerchantRequested(AccountId accountId) {
        messageQueue.publish(QueueNames.REGISTER_MERCHANT_REQUESTED, new Event(new Object[]{ accountId }));
    }

    @SneakyThrows
    private User addRegisterMerchantReturnedSubscriber() {
        CompletableFuture<Event> completableFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(
                QueueNames.REGISTER_MERCHANT_RETURNED,
                completableFuture::complete);

        Event event = completableFuture.join();
        user = event.getArgument(0, User.class);
        String message = event.getArgument(1, String.class);

        if (channel != null)
            channel.close();
        if (user == null)
            throw new RuntimeException(message);
        return user;
    }

    private void publishInitiatePaymentRequest(InitiatePaymentDTO initiatePaymentDTO) {
        initiatePaymentDTO.setMerchantUser(user);
        messageQueue.publish(QueueNames.INITIATE_PAYMENT_REQUESTED, new Event(new Object[]{ initiatePaymentDTO }));
    }

    @SneakyThrows
    private String addInitiatePaymentReturnedSubscriber() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(
                QueueNames.INITIATE_PAYMENT_RETURNED,
                getPaymentInitiatedConsumer(completableFuture));
        String message = completableFuture.join();
        if (channel != null)
            channel.close();
        return message;
    }

    private Consumer<Event> getPaymentInitiatedConsumer(CompletableFuture<String> completableFuture) {
        return (event) -> completableFuture.complete(event.getArgument(0, String.class));
    }

}
