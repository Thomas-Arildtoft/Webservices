package dk.dtu.pay.paymentmanagement;

import dk.dtu.pay.utils.messaging.RabbitMQQueue;
import lombok.SneakyThrows;

public class StartUp {

    @SneakyThrows
    public static void main(String[] args) {
        new Service(RabbitMQQueue.getInstance());
    }

}
