package dk.dtu.pay.merchant;

import com.rabbitmq.client.Channel;
import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

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

    private void publishAccountIdToRegister(AccountId accountId) {
        messageQueue.publish(QueueNames.REGISTER_MERCHANT_REQUESTED, new Event(new Object[]{ accountId }));
    }

    @SneakyThrows
    private User consumeRegisteredUser() {
        CompletableFuture<User> completableFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(
                QueueNames.REGISTER_MERCHANT_RETURNED,
                getConsumer(completableFuture));
        user = completableFuture.join();
        if (channel != null)
            channel.close();
        if (user == null)
            throw new RuntimeException("Registration Failed");
        return user;
    }

    private Consumer<Event> getConsumer(CompletableFuture<User> completableFuture) {
        return (event) -> completableFuture.completeOnTimeout(event.getArgument(0, User.class), QUEUE_TIMEOUT, TimeUnit.SECONDS);
    }
}
