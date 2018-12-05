package com.drafire.distributed.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;

/**
 * rabbitmq 的一些参数配置和公共方法
 */
public class RabbitMQHelper {
    public static final String ADDR = "192.168.109.128";
    public static final String PORT = "5672";

    //自动ack
    public static final boolean AUTO_ACK = Boolean.TRUE;
    //非自动ACK
    public static final boolean NO_AUTO_ACK = Boolean.FALSE;
    //持久化
    public static final boolean DURABLE = Boolean.TRUE;
    //非持久化
    public static final boolean NO_DURABLE = Boolean.FALSE;
    //预处理数量，为了避免造成旱的旱死，涝的涝死，可以使用这个配置
    public static final int PREFETCH_COUNT = 1;

    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQHelper.ADDR);
        factory.setPassword("admin");
        factory.setUsername("admin");
        return factory;
    }
}
