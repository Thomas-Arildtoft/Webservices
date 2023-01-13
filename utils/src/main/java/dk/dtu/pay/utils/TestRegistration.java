package dk.dtu.pay.utils;

import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.messaging.RabbitMQQueue;
import dk.dtu.pay.utils.models.AccountId;
import dk.dtu.pay.utils.models.Role;
import dk.dtu.pay.utils.models.User;

public class TestRegistration {

    public static void main(String[] args) {
        MessageQueue messageQueue = RabbitMQQueue.getInstance();

        messageQueue.addHandler(QueueNames.REGISTER_CUSTOMER_REQUESTED,
                (event) -> {
                    AccountId accountId = event.getArgument(0, AccountId.class);
                    User user = new User(accountId.getId(), Role.CUSTOMER);
                    messageQueue.publish(QueueNames.REGISTER_CUSTOMER_RETURNED, new Event(new Object[]{user}));
                });
    }
}
