package com.drafire.distributed.rabbitmq.routing;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

/**
 * 使用direct模式，进行发布/订阅
 */
public class EmitLogsDirect {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = RabbitMQHelper.getConnectionFactory();

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel();) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            Scanner scanner = new Scanner(System.in);

            int a = 1;
            while (a > 0) {
                if (scanner.hasNext()) {
                    String input = scanner.nextLine();
                    String[] inputArr = input.split("\\|");

                    channel.basicPublish(EXCHANGE_NAME, inputArr[0], null, inputArr[1].getBytes("UTF-8"));
                    System.out.println(" 服务器使用" + inputArr[0] + "模式 Sent '" + inputArr[1] + "'");
                }

            }
        }
    }
}
