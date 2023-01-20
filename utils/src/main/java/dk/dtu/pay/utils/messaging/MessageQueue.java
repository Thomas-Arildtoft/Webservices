package dk.dtu.pay.utils.messaging;

import com.rabbitmq.client.Channel;

import java.util.function.Consumer;

/**
 * @author Ali
 */
 
public interface MessageQueue {

    void publish(String queueName, Event message);
    Channel addHandler(String queueName, Consumer<Event> handler);

}
