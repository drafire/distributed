package com.drafire.distributed.zookeeper.javaapiDemo;

import com.drafire.distributed.zookeeper.ConnectionHelper;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 权限控制demo
 */
public class AuthDemo implements Watcher {

    private static final CountDownLatch countDownLatch = ConnectionHelper.getCountDownLatch();

    private static final Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final ZooKeeper zooKeeper = new ZooKeeper(ConnectionHelper.CONNECTION, 1000 * 5, new AuthDemo());

        countDownLatch.await();

        //账号：密码模式
        ACL acl1 = new ACL(ZooDefs.Perms.CREATE, new Id("digest", "root:pang123456a"));
        //ip模式
        ACL acl2 = new ACL(ZooDefs.Perms.READ, new Id("ip", "192.168.1.1"));
        List<ACL> list = new ArrayList<>();
        list.add(acl1);
        list.add(acl2);

        zooKeeper.create("/auth1", "auth1".getBytes(), list, CreateMode.PERSISTENT);
        zooKeeper.addAuthInfo("digest", "root:pang123456a".getBytes());
        //下面这个会重复创建节点，抛出异常
        //zooKeeper.create("/auth1", "auth1".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        //这个会抛出异常，提示没有权限，因为只有list里面的ACL才有权限
        //zooKeeper.create("/auth1/auth1-1", "234".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        //这里会抛出没有读的权限
//        byte[] data = zooKeeper.getData("/auth1", false, stat);
//        System.out.println("/auth1 的当前值：" + data.toString());

        ZooKeeper zooKeeper1 = new ZooKeeper(ConnectionHelper.CONNECTION, 1000 * 5, new AuthDemo());
        //这里需要做阻塞，因为不阻塞，会继续用这个session，导致有了删除的权限？为什么呢，异步的问题？
        countDownLatch.await();
        zooKeeper1.delete("/auth1", -1);

    }

    @Override
    public void process(WatchedEvent event) {
        //如果当前的连接状态是连接成功的，那么通过计数器去控制
        if (event.getState() == Event.KeeperState.SyncConnected) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                countDownLatch.countDown();
                System.out.println(event.getState() + "->" + event.getType());
            }
        }
    }
}
