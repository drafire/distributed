package com.drafire.distributed.rabbitmq.topic;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

/**
 * topic 模式的生产者
 */
public class EmitLogTopicSender {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = RabbitMQHelper.getConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        int a = 1;
        while (a > 0) {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String input = scanner.nextLine();
                String[] inputArr = input.split("\\|");
                String rountingKey = inputArr[0];
                String message = inputArr[1];

                channel.basicPublish(EXCHANGE_NAME, rountingKey, null,
                        message.getBytes("UTF-8"));
                System.out.println("服务端发送【" + rountingKey + "】【" + message + "】");
            }
        }

        channel.close();
        connection.close();
    }
}
