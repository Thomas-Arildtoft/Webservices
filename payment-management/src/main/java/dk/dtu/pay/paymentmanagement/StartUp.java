package dk.dtu.pay.paymentmanagement;

import dk.dtu.pay.utils.messaging.RabbitMQQueue;
import lombok.SneakyThrows;

/**
 * @author Salim
 */
 
public class StartUp {

    @SneakyThrows
    public static void main(String[] args) {
        new Service(RabbitMQQueue.getInstance());
    }

}
