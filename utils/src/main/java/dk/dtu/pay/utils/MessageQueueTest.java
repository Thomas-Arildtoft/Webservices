package dk.dtu.pay.utils;

import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.messaging.RabbitMQQueue;
import dk.dtu.pay.utils.models.User;

public class MessageQueueTest {

    public static void main(String[] args) {
        MessageQueue messageQueue = RabbitMQQueue.getInstance();
        messageQueue.publish(QueueNames.USER_REQUESTED, new Event(new Object[]{ "c3825a5c-f327-46ef-881a-09e4631edd4e" }));

        messageQueue.addHandler(QueueNames.USER_RETURNED,
                (event) -> {
                    User user = event.getArgument(0, User.class);
                    System.out.println(user.getId());
                });
    }
}
