package com.drafire.distributed.zookeeper;

import java.util.concurrent.CountDownLatch;

/**
 * zookeeper连接字符串
 */
public class ConnectionHelper {

    public static final String CONNECTION=
            "192.168.141.128:2181," +
            "192.168.141.129:2181," +
            "192.168.141.130:2181," +
            "192.168.141.131:2181";

    private static CountDownLatch countDownLatch;

    public static CountDownLatch getCountDownLatch() {
        countDownLatch=new CountDownLatch(1);
        return countDownLatch;
    }
}
