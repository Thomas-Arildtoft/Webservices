package dk.dtu.pay.utils.messaging;

import java.util.function.Consumer;

public interface MessageQueue {

    void publish(String queueName, Event message);
    void addHandler(String eventType, Consumer<Event> handler);

}
