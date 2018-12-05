package com.drafire.distributed.rabbitmq.workerqueues;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.*;

import java.util.concurrent.TimeUnit;

public class Worker {
    private static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = RabbitMQHelper.getConnectionFactory();

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(RabbitMQHelper.PREFETCH_COUNT);

        DeliverCallback deliverCallback = ((consumerTag, message) -> {
            String receiveMessage = new String(message.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

            try {
                dowork(receiveMessage);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        });


    }

    private static void dowork(String task) {
        for (char ch : task.toCharArray()) {
            if('.'==ch){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
