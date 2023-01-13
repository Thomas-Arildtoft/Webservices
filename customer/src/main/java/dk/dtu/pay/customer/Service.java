package dk.dtu.pay.customer;

import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.messaging.RabbitMQQueue;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.User;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Service {

    private static final long QUEUE_TIMEOUT = 1; //seconds

    private MessageQueue messageQueue = RabbitMQQueue.getInstance();
    private User user = null;

    public User register(AccountId accountId) {
        publishAccountIdToRegister(accountId);
        return consumeRegisteredUser();
    }

    private void publishAccountIdToRegister(AccountId accountId) {
        messageQueue.publish(QueueNames.REGISTER_CUSTOMER_REQUESTED, new Event(new Object[]{ accountId }));
    }

    @SneakyThrows
    private User consumeRegisteredUser() {
        CompletableFuture<User> completableFuture = new CompletableFuture<>();
        Channel channel = messageQueue.addHandler(QueueNames.REGISTER_CUSTOMER_RETURNED,
                (event) -> {
                    completableFuture.completeOnTimeout(event.getArgument(0, User.class), QUEUE_TIMEOUT, TimeUnit.SECONDS);
                });
        user = completableFuture.join();
        channel.close();
        return user;
    }
}
