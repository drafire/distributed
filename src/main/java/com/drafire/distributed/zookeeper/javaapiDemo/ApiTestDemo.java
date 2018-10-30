package com.drafire.distributed.zookeeper.javaapiDemo;

import com.drafire.distributed.zookeeper.ConnectionHelper;
import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * api操作demo
 */
public class ApiTestDemo implements Watcher {

    private static ZooKeeper zooKeeper;

    private static CountDownLatch countDownLatch = ConnectionHelper.getCountDownLatch();

    private static Stat stat = new Stat();

    public static void main(String[] args) {
        try {
            zooKeeper = new ZooKeeper(ConnectionHelper.CONNECTION, 1000 * 2, new ApiTestDemo());
            //线程阻塞
            countDownLatch.await();
            States states = zooKeeper.getState();
            if (zooKeeper.getState() == States.CONNECTED) {
                System.out.println("zookeeper 连接成功啦:" + states.name());
            }

            //创建节点
//            zooKeeper.create("/drafire2018103002","drafire2018103002".getBytes(),
//                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            //创建子节点
            //zooKeeper.create("/drafire/drafire1-1","第一个临时子节点".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
            //修改节点值  -1代表匹配任何版本 这个版本有些类似于乐观锁的意思
            //zooKeeper.setData("/drafire2018103002", "002再次被修改".getBytes(), -1);
            //删除节点 ---如果没有节点，会抛出异常
            //zooKeeper.delete("/drafire2018103001",-1);
            //查询子节点
            zooKeeper.create("/drafire/drafire2", "临时自节点".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            List<String> list = zooKeeper.getChildren("/drafire", true);
            System.out.println("/drafire的子节点如下：" + list.toString());
            //查询节点
            byte[] data = zooKeeper.getData("/drafire2018103002", true, stat);
            System.out.println("/drafire2018103002：" + data.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            //连接但是还没连接上，则计数
            if (null == event.getPath() && Event.EventType.None == event.getType()) {
                countDownLatch.countDown();
                System.out.println(event.getState() + "->" + event.getType());
            }
        }
    }
}
