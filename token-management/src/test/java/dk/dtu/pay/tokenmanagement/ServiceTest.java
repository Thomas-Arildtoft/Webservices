package dk.dtu.pay.tokenmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.function.Consumer;

import com.rabbitmq.client.*;
import dk.dtu.pay.utils.messaging.*;
import dk.dtu.pay.utils.models.*;
import org.junit.Before;
import org.junit.Test;

public class ServiceTest {
    private Service service;
    private MessageQueue messageQueue;
    private Repository repository;
    private User user;

    @Before
    public void setUp() {
        messageQueue = new MessageQueue() {
            @Override
            public void publish(String queueName, Event message) {
                if (queueName.equals(QueueNames.TOKENS_RETURNED)) {
                    messageQueue.addHandler(QueueNames.TOKENS_REQUESTED, (event) -> {
                        messageQueue.publish(QueueNames.TOKENS_RETURNED, new Event(new Object[]{Collections.singletonList("token")}));
                    });
                } else if (queueName.equals(QueueNames.USER_FROM_TOKEN_RETURNED)) {
                    messageQueue.addHandler(QueueNames.USER_FROM_TOKEN_REQUESTED, (event) -> {
                        messageQueue.publish(QueueNames.USER_FROM_TOKEN_RETURNED, new Event(new Object[]{user}));
                    });
                }
            }

            @Override
            public Channel addHandler(String queueName, Consumer<Event> handler) {
                return null;
            }

            @Override
            public void publishandWait(String queueName, Event message) {

            }
        };
        repository = new Repository();
        service = new Service(messageQueue);
    }

    @Test
    public void testUserFromTokenRequestedSubscriber() {
        // Add a token to the repository
       user = new User();
       user.setId("id");
        repository.put(user, Collections.singletonList("test-token"));



        // Send a message to retrieve the user from the token

        // Assert that the returned user is the same as the added user
        assertEquals(user, repository.findUserAndRemoveToken("test-token"));
        // Assert that the token has been removed from the repository
        assertNull(repository.findUserAndRemoveToken("test-token"));
    }

    @Test
    public void testUserFromInvalidTokenRequestedSubscriber() {
        // Send a message to retrieve the user from an invalid token
        messageQueue.publish(QueueNames.USER_FROM_TOKEN_REQUESTED, new Event(new Object[]{"invalid-token"}));

        // Assert that no user is returned
        assertNull(repository.findUserAndRemoveToken("invalid"));
    }
}
