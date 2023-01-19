package dk.dtu.pay.customer;

import com.rabbitmq.client.Channel;
import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.TokenRequest;
import dk.dtu.pay.utils.models.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Service {

    private final MessageQueue messageQueue;
    private User user = null;

    public User register(AccountId accountId) {
        publishRegisterCustomerRequested(accountId);
        return addRegisterCustomerReturnedSubscriber();
    }

    public List<String> getTokens(int numberOfTokens) {
        publishTokenRequested(numberOfTokens);
        return addTokensReturnedSubscriber();
    }

    public void clean() {
        user = null;
        messageQueue.publish(QueueNames.CLEAN_ACCOUNT_MANAGEMENT_REQUESTED, new Event(null));
        messageQueue.publish(QueueNames.CLEAN_TOKEN_MANAGEMENT_REQUESTED, new Event(null));
    }

    private void publishRegisterCustomerRequested(AccountId accountId) {
        messageQueue.publish(QueueNames.REGISTER_CUSTOMER_REQUESTED, new Event(new Object[]{ accountId }));
    }

    @SneakyThrows
    private User addRegisterCustomerReturnedSubscriber() {
        CompletableFuture<Event> completableFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(
                QueueNames.REGISTER_CUSTOMER_RETURNED,
                completableFuture::complete);

        Event event = completableFuture.join();
        User user = event.getArgument(0, User.class);
        String message = event.getArgument(1, String.class);

        if (channel != null)
            channel.close();
        if (user == null)
            throw new RuntimeException(message);
        return user;
    }

    private void publishTokenRequested(int numOfTokens) {
        messageQueue.publish(QueueNames.TOKENS_REQUESTED, new Event(new Object[]{ new TokenRequest(user, numOfTokens)}));
    }

    @SneakyThrows
    private List<String> addTokensReturnedSubscriber() {
        CompletableFuture<Event> completableFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(
                QueueNames.TOKENS_RETURNED,
                completableFuture::complete);

        Event event = completableFuture.join();
        List<String> tokens = event.getArgument(0, List.class);
        String message = event.getArgument(1, String.class);

        if (channel != null)
            channel.close();
        if (tokens == null)
            throw new RuntimeException(message);
        return tokens;
    }

}
