package com.drafire.distributed.rabbitmq.publish_subscribe;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveAction {
    private static final String EXCHANGE_NAME = "logs";

    public static void receive(String receiverName) throws Exception {
        ConnectionFactory factory = RabbitMQHelper.getConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明一个交换器，使用fanout模式
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //获取一个随机的队列名称
        String queueName = channel.queueDeclare().getQueue();
        //把队列和交换器绑定起来
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(receiverName + " Waiting for messages. To exit press CTRL+C");

        //消费回调
        DeliverCallback deliverCallback = ((consumerTag, message) -> {
            String data = new String(message.getBody(), "UTF-8");
            System.out.println(receiverName + " receive" + data);
        });

        channel.basicConsume(queueName, RabbitMQHelper.AUTO_ACK, deliverCallback, consumerTag -> {
        });
    }
}
