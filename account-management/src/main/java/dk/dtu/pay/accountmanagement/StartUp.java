package dk.dtu.pay.accountmanagement;

import dk.dtu.pay.utils.messaging.RabbitMQQueue;
import lombok.SneakyThrows;

/**
 * @author Ali
 */

public class StartUp {

    @SneakyThrows
    public static void main(String[] args) {
        new Service(RabbitMQQueue.getInstance());
    }

}
