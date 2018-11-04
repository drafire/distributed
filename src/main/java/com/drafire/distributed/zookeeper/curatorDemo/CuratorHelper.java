package com.drafire.distributed.zookeeper.curatorDemo;

import com.drafire.distributed.zookeeper.ConnectionHelper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * curator 帮助类
 */
public class CuratorHelper {
    private static CuratorFramework curatorFramework;

    /**
     * 构造方法的方式得到一个CuratorFramework
     * @return
     */
    public static CuratorFramework getInstance() {
        curatorFramework = CuratorFrameworkFactory.newClient(ConnectionHelper.CONNECTION, 1000 * 5,
                1000 * 5, new ExponentialBackoffRetry(1000 * 1, 3));
        curatorFramework.start();
        return curatorFramework;
    }

    /**
     * fluent方式得到一个CuratorFramework
     * @return
     */
    public static CuratorFramework getCuratorFramework(){
        curatorFramework=CuratorFrameworkFactory.builder().connectString(ConnectionHelper.CONNECTION).sessionTimeoutMs(1000*5)
                .connectionTimeoutMs(1000*4).retryPolicy(new ExponentialBackoffRetry(1000 * 1, 3)).build();
        curatorFramework.start();
        return curatorFramework;
    }

    public static void closeClient(CuratorFramework curatorFramework){
        curatorFramework.close();
    }
}
