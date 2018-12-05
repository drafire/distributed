package com.drafire.distributed.rabbitmq.routing;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class RoutingAction {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void receive(String receiverName,String level) throws Exception {
        ConnectionFactory factory = RabbitMQHelper.getConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, level);
        System.out.println(receiverName + " -> Waiting for messages. To exit press CTRL+C");

        DeliverCallback callback = (consumerTag, message) -> {
            String data = new String(message.getBody(), "UTF-8");
            System.out.println(receiverName + "->接收到direct->" + message.getEnvelope().getRoutingKey() + ":" + data);
        };

        channel.basicConsume(queueName, RabbitMQHelper.AUTO_ACK, callback, consumerTag -> {
        });
    }
}
