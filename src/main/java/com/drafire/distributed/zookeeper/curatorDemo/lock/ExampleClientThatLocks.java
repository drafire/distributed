package com.drafire.distributed.zookeeper.curatorDemo.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

public class ExampleClientThatLocks {
    //进程锁S
    private final InterProcessMutex lock;
    //模拟资源
    private final FakeLimitedResource resource;
    //客户端名称
    private final String clientName;

    public ExampleClientThatLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource, String clientName) {
        this.lock = new InterProcessMutex(client, lockPath);
        this.resource = resource;
        this.clientName = clientName;
    }

    public void dowork(long time, TimeUnit unit) throws Exception {
        //acquire：在time个unit单位内尝试获得锁。如果unit为null，则默认使用微秒。
        //创建znode 在这里，PATH 创建的是无序的永久节点，但是PATH下还会再创建一个临时有序的子节点，如：/examples/locks/_c_a18de879-8da7-4d7e-93ea-ed2c29525311-lock-0000000003
        if (!lock.acquire(time, unit)) {
            throw new IllegalStateException(clientName + " 不能获得锁");
        }
        try {
            System.out.println(clientName + " 获得锁");
            resource.use();
        } finally {
            System.out.println(clientName + " 释放锁");
            lock.release();
        }
    }
}
