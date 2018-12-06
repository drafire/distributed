package com.drafire.distributed.rabbitmq.RPC;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * RPC客户端
 * rpc_queue这个队列中，Client 是生产者，Server是消费者
 */
public class Client implements AutoCloseable {
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";

    public Client() throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitMQHelper.getConnectionFactory();
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public static void main(String[] args) {
        try (Client client = new Client();) {
            for (int i = 0; i < 10; i++) {
                String i_str = Integer.toString(i);
                System.out.println(" client Requesting fib(" + i_str + ")");
                String response = client.call(i_str);
                System.out.println(" client Got '" + response + "'");
            }
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String call(String i_str) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        //在replyQueueName 这个队列中，Client是消费者，Server是生产者
        String replyQueueName = channel.queueDeclare().getQueue();

        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)   //声明correlationId
                .replyTo(replyQueueName)  //声明回调的队列
                .build();                 //返回实例，调用的是构造方法

        // 生产消息
        channel.basicPublish("", requestQueueName, props, i_str.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, message) -> {
            if (message.getProperties().getCorrelationId().equals(corrId)) {
                //把接收到的消息插入队列中
                response.offer(new String(message.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });

        //从队列中拿出结果
        String result = response.take();
        //删除channel
        // 从源代码中可以看出，队列是放在一个map中的：Map<String, RecordedQueue>
        channel.basicCancel(ctag);
        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
