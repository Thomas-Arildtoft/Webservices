package dk.dtu.pay.utils;

import dk.dtu.pay.utils.messaging.Event;
import dk.dtu.pay.utils.messaging.MessageQueue;
import dk.dtu.pay.utils.messaging.QueueNames;
import dk.dtu.pay.utils.messaging.RabbitMQQueue;
import dk.dtu.pay.utils.models.InitiatePaymentDTO;
import dk.dtu.pay.utils.models.User;

public class MessageQueueTest {

    public static void main(String[] args) {
        MessageQueue messageQueue = RabbitMQQueue.getInstance();
        messageQueue.addHandler(QueueNames.INITIATE_PAYMENT_REQUESTED,
                (event) -> {
                    InitiatePaymentDTO dto = event.getArgument(0, InitiatePaymentDTO.class);
                    System.out.println(dto.getMerchantUser());
                    System.out.println(dto.getCustomerToken());
                    System.out.println(dto.getAmount());
                    messageQueue.publish(QueueNames.INITIATE_PAYMENT_RETURNED, new Event(new Object[]{ "Failed hehe" }));
                });
    }
}
