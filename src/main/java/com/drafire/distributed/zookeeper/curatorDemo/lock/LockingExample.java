package com.drafire.distributed.zookeeper.curatorDemo.lock;

import com.drafire.distributed.zookeeper.curatorDemo.CuratorHelper;
import org.apache.curator.framework.CuratorFramework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LockingExample {

    private static final int QTY = 5;
    private static final int REPETITIONS = QTY * 10;

    private static final String PATH = "/examples/locks";

    public static void main(String[] args) {
        final FakeLimitedResource resource = new FakeLimitedResource();

        ExecutorService service = Executors.newFixedThreadPool(QTY);

        for (int i = 0; i < QTY; i++) {
            final int index = i;
            Callable<Void> task = new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    CuratorFramework client = CuratorHelper.getCuratorFramework();

                    //这里创建的锁(子节点)是一个无序的持久的znode
                    ExampleClientThatLocks example = new ExampleClientThatLocks(client, PATH, resource, "Client " + index);

                    for (int j = 0; j < REPETITIONS; j++) {
                        example.dowork(10, TimeUnit.SECONDS);
                    }
                    return null;
                }
            };
            service.submit(task);
        }
        service.shutdown();
        try {
            service.awaitTermination(10,TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
