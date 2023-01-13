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
        User user = new User("eed2c745-6c6f-4194-9f7a-182f9823e83", Role.CUSTOMER);
        messageQueue.publish(QueueNames.TM_ACCOUNT_REQUESTED, new Event(new Object[]{user}));

        messageQueue.addHandler(QueueNames.TM_ACCOUNT_RETURNED,
                (event) -> {
                    AccountId accountId = event.getArgument(0, AccountId.class);
                    System.out.println(accountId);
                });
    }
}
