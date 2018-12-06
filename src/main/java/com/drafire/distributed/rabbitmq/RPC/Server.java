package com.drafire.distributed.rabbitmq.RPC;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.*;

/**
 * RPC服务端
 * * rpc_queue这个队列中，Client 是生产者，Server是消费者
 */
public class Server {
    private static final String RPC_QUEUE_NAME = "rpc_queue";

    private static int fib(int n) {
        if (0 == n) {
            return 0;
        } else if (1 == n) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = RabbitMQHelper.getConnectionFactory();

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            //声明队列
            channel.queueDeclare(RPC_QUEUE_NAME, RabbitMQHelper.DURABLE, RabbitMQHelper.NO_EXCLUSIVE, RabbitMQHelper.NO_AUTO_DELETE, null);
            //清理队列
            channel.queuePurge(RPC_QUEUE_NAME);
            //每次只处理一个请求
            channel.basicQos(RabbitMQHelper.PREFETCH_COUNT);
            System.out.println(" server is Awaiting RPC requests");

            Object monitor = new Object();
            //Server是生产者同时也是消费者，接受来自消费者Client的消息
            DeliverCallback deliverCallback = ((consumerTag, deliver) -> {
                //根据接收到的correlationId，设置BasicProperties
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(deliver.getProperties().getCorrelationId())
                        .build();

                String response = "";

                try {
                    String data = new String(deliver.getBody(), "UTF-8");
                    int n = Integer.parseInt(data);

                    System.out.println(" [.] fib(" + new String(deliver.getBody(), "UTF-8") + ")");
                    //递归计算结果
                    response += fib(n);
                } finally {
                    //生产消息，被Client消费
                    channel.basicPublish("", deliver.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    channel.basicAck(deliver.getEnvelope().getDeliveryTag(), false);

                    //由于线程一直在沉睡，需要唤醒线程
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            });

            //消费来自于Client的消息
            channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, consumerTag -> {
            });

            //一直等待消息，因为Server也是消费者
            int a = 1;
            while (a > 0) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
