package dk.dtu.pay.utils.messaging;

import com.rabbitmq.client.Channel;

import java.util.function.Consumer;

public interface MessageQueue {

    void publish(String queueName, Event message);
    Channel addHandler(String queueName, Consumer<Event> handler);
    void publishandWait(String queueName, Event message);

}
