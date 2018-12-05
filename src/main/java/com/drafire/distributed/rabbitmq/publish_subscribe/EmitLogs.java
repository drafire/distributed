package com.drafire.distributed.rabbitmq.publish_subscribe;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

/**
 * 发布/订阅模式的生产者（fanout模式）
 */
public class EmitLogs {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = RabbitMQHelper.getConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            Scanner scanner = new Scanner(System.in);
            int a = 1;
            while (a > 0) {
                if(scanner.hasNext()){
                    String message = scanner.nextLine();
                    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                    System.out.println(" [x] Sent '" + message + "'");
                }

            }

        }
    }
}
