package com.drafire.distributed.rabbitmq.publish_subscribe;

/**
 * 发布/订阅模式，消费者
 */
public class ReceiveLogs1 {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ReceiveAction.receive("消费者1");
    }
}
