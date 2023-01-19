package dk.dtu.pay.utils.messaging;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class RabbitMQQueue implements MessageQueue {
    /***
     *
     *
     *
     *
     * */
    private static final String DEFAULT_HOSTNAME = "localhost";

    private String hostname;

    public RabbitMQQueue() {
        this(DEFAULT_HOSTNAME);
    }

    @SneakyThrows
    public RabbitMQQueue(String hostname) {
        this.hostname = hostname;
    }

    public static MessageQueue getInstance() {
        return new RabbitMQQueue("rabbitMq");
    }

    @SneakyThrows
    @Override
    public void publish(String queueName, Event event) {
        try (Connection connection = getConnection();) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);
            String message = new Gson().toJson(event);
            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
        }
    }

    @SneakyThrows
    @Override
    public Channel addHandler(String queueName, Consumer<Event> handler) {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            Event event = new Gson().fromJson(message, Event.class);
            handler.accept(event);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
        return channel;
    }

    @SneakyThrows
    private Connection getConnection() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostname);
        return factory.newConnection();
    }

}
