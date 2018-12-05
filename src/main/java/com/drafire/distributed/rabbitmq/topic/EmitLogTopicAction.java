package com.drafire.distributed.rabbitmq.topic;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class EmitLogTopicAction {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void receive(String receiverName, String rout) throws Exception {
        ConnectionFactory factory = RabbitMQHelper.getConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, rout);

        System.out.println(receiverName + "正在接收消息");

        DeliverCallback deliverCallback = (consumerTag, deliver) -> {
            String message = new String(deliver.getBody(), "UTF-8");
            System.out.println(receiverName + "接收到->" + deliver.getEnvelope().getRoutingKey() + ":" + message);
        };

        channel.basicConsume(queueName, RabbitMQHelper.AUTO_ACK, deliverCallback, consumerTag -> {
        });
    }
}
