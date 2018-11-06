package com.drafire.distributed.zookeeper.curatorDemo.lock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 模拟资源
 */
public class FakeLimitedResource {
    private final AtomicBoolean inUse = new AtomicBoolean(false);

    public void use() {
        if (!inUse.compareAndSet(false, true)) {
            throw new IllegalStateException("同一时间只能被一个客户端访问");
        }

        try {
            Thread.sleep((long) (3 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            inUse.set(false);
        }
    }
}
