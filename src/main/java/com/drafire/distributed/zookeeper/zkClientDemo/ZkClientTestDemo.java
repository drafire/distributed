package com.drafire.distributed.zookeeper.zkClientDemo;

import com.drafire.distributed.zookeeper.ConnectionHelper;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * zk 客户端操作demo
 */
public class ZkClientTestDemo {
    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient(ConnectionHelper.CONNECTION, 1000 * 5);

        //创建持久化节点
        //zkClient.createPersistent("/zkClient1/zkClient1-1/zkClient1-1-1", true);

        //创建临时节点，因为节点通讯是异步的，所以两次的程序执行的时候，有可能会提示临时节点存在，因为删除还没同步
        //zkClient.createEphemeral("/zkClient1/zkClient2-1");

        //查询节点，这个方法没用的
//        Object data = zkClient.readData("/zkClient1");
//        System.out.println(data.toString());

        //查询子节点
        List<String> list = zkClient.getChildren("/zkClient1");
        System.out.println(list);
        for (String children : list) {
            System.out.println(children.toString());
        }

        //增加监控
        zkClient.subscribeDataChanges("/zkClient1", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("节点：" + s + "修改后的值是：" + o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                //System.out.println("节点：" + s);
            }
        });
        zkClient.writeData("/zkClient1","zkClient 被zkClient的方式修改啦");

        //因为异步通讯，需要沉睡一定时间才能有监控到
        TimeUnit.SECONDS.sleep(2);

        zkClient.subscribeChildChanges("/zkClient1", new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println("节点：" + s + "修改后的的子节点是：" + list);
            }
        });

        //增加子节点才能触发subscribeChildChanges的watcher
        zkClient.createPersistent("/zkClient1/zkClient3-1");
        //修改原有的子节点不能触发subscribeChildChanges的watcher
        zkClient.writeData("/zkClient1/zkClient1-1","zkClient的子节点被zkClient的方式修改啦");
        TimeUnit.SECONDS.sleep(2);
    }
}
