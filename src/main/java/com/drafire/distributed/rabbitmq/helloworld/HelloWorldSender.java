package com.drafire.distributed.rabbitmq.helloworld;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.time.LocalDateTime;

/**
 * rabbitmq 生产者，hellow world
 */
public class HelloWorldSender {
    private final static String QUEUE_NAME = "hello";

    /**
     * 队列声明了自后，就不再被允许修改参数，只能重新建立一个队列
     * 队列的声明需要在生产者和消费者两（多（个消费者））端同时声明
     */
    public static void main(String[] args) throws Exception {
        //新建一个工厂
        ConnectionFactory factory = RabbitMQHelper.getConnectionFactory();
        //设定mq地址
        factory.setHost(RabbitMQHelper.ADDR);
        //声明一个Connection
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //这里声明一个queue，只有queue 不存在的时候，才会增加这个queue
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "hello world,drafire" + LocalDateTime.now();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
