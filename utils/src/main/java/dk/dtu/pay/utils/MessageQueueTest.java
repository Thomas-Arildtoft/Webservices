package dk.dtu.pay.utils;

import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.messaging.RabbitMQQueue;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.Role;
import dk.dtu.pay.utils.models.User;

public class MessageQueueTest {

    public static void main(String[] args) {
        MessageQueue messageQueue = RabbitMQQueue.getInstance();
        User user = new User("34a16bc6-be7a-49e0-8950-4906f7c762c2", Role.CUSTOMER);
        messageQueue.publish(QueueNames.TM_ACCOUNT_REQUESTED, new Event(new Object[]{user}));

        messageQueue.addHandler(QueueNames.TM_ACCOUNT_RETURNED,
                (event) -> {
                    AccountId accountId = event.getArgument(0, AccountId.class);
                    System.out.println(accountId);
                });
    }
}
