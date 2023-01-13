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
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Service {

    private static final long QUEUE_TIMEOUT = 1; //seconds

    private final MessageQueue messageQueue;
    private User user = null;

    public User register(AccountId accountId) {
        publishAccountIdToRegister(accountId);
        return consumeRegisteredUser();
    }

    public List<String> getTokens(int numberOfTokens) {
        publishTokenRequested(numberOfTokens);
        return consumerTokenRequested();
    }

    private void publishAccountIdToRegister(AccountId accountId) {
        messageQueue.publish(QueueNames.REGISTER_CUSTOMER_REQUESTED, new Event(new Object[]{ accountId }));
    }

    @SneakyThrows
    private User consumeRegisteredUser() {
        CompletableFuture<User> completableFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(
                QueueNames.REGISTER_CUSTOMER_RETURNED,
                getRegisteredUserConsumer(completableFuture));
        user = completableFuture.join();
        if (channel != null)
            channel.close();
        if (user == null)
            throw new RuntimeException("Registration Failed");
        return user;
    }

    private void publishTokenRequested(int numOfTokens) {
        messageQueue.publish(QueueNames.TOKENS_REQUESTED, new Event(new Object[]{ new TokenRequest(user, numOfTokens)}));
    }

    @SneakyThrows
    private List<String> consumerTokenRequested() {
        CompletableFuture<List<String>> completableFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(
                QueueNames.TOKENS_RETURNED,
                getTokenRequestedConsumer(completableFuture));
        List<String> tokens = completableFuture.join();
        if (channel != null)
            channel.close();
        if (tokens == null)
            throw new RuntimeException("Registration Failed");
        return tokens;
    }

    private Consumer<Event> getRegisteredUserConsumer(CompletableFuture<User> completableFuture) {
        return (event) -> completableFuture.completeOnTimeout(event.getArgument(0, User.class), QUEUE_TIMEOUT, TimeUnit.SECONDS);
    }

    private Consumer<Event> getTokenRequestedConsumer(CompletableFuture<List<String>> completableFuture) {
        return (event) -> completableFuture.completeOnTimeout(event.getArgument(0, List.class), QUEUE_TIMEOUT, TimeUnit.SECONDS);
    }

}
