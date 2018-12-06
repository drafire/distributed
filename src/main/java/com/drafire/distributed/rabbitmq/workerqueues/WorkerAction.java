package com.drafire.distributed.rabbitmq.workerqueues;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WorkerAction {
    private static final String QUEUE_NAME = "work_queue";

    public static void receive(String reveiverName) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitMQHelper.getConnectionFactory();

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(RabbitMQHelper.PREFETCH_COUNT);

        DeliverCallback deliverCallback = ((consumerTag, message) -> {
            String receiveMessage = new String(message.getBody(), "UTF-8");
            System.out.println(" [" + reveiverName + "] Received '" + message + "'");

            try {
                dowork(receiveMessage);
            } finally {
                System.out.println(" [" + reveiverName + "] Done");
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        });

        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
    }

    private static void dowork(String task) {
        for (char ch : task.toCharArray()) {
            if ('.' == ch) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
