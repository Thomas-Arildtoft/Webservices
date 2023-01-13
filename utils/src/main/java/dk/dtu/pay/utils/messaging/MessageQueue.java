package dk.dtu.pay.utils.messaging;

import com.rabbitmq.client.Channel;

import java.util.function.Consumer;

public interface MessageQueue {

    void publish(String queueName, Event message);
    Channel addHandler(String eventType, Consumer<Event> handler);

}
