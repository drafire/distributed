package com.drafire.distributed.redis;

/**
 * 发布-订阅 测试类
 */
public class PubSubTest {
    public static void main(String[] args) {
        SubThread subThread=new SubThread();
        subThread.start();

        Publisher publisher=new Publisher();
        publisher.start();
    }
}
