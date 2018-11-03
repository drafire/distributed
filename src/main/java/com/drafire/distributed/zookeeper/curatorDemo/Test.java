package com.drafire.distributed.zookeeper.curatorDemo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;

public class Test {
    public static void main(String[] args) throws Exception {
        //这里不用像原生的api那样countdown，好爽，但是看日志的时候，有个等待的过程
        CuratorFramework curatorFramework = ClientUnit.getInstance();
        System.out.println("连接成功");

        //fluent风格，也就是链式编程
        //缺省withMode的时候，默认创建的Persistent
        //String result= curatorFramework.create().creatingParentsIfNeeded().forPath("/curator/curator1-1");
        //创建临时节点
//        String result=curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
//                .forPath("/curator/curator2-1");


//        Stat stat1=new Stat();
//        byte[] data1 = curatorFramework.getData().storingStatIn(stat1).forPath("/curator");
//        System.out.println("节点是：" + new String(data1)+"->"+stat1);

        //更新节点
        //curatorFramework.setData().forPath("/curator","curator 更新节点啦".getBytes());

        //查询节点
//        Stat stat=new Stat();
//        byte[] data = curatorFramework.getData().storingStatIn(stat).forPath("/curator");
//        System.out.println("节点是：" + new String(data)+"->"+stat);


        //delete删除节点，如果有子节点，则不能直接删除
        //curatorFramework.delete().forPath("/curator");
        //deletingChildrenIfNeeded也可以删除子节点，如果有子节点
        //curatorFramework.delete().deletingChildrenIfNeeded().forPath("/curator");

        //curator 独有事务操作
//        Collection<CuratorTransactionResult> results = curatorFramework.inTransaction().create().forPath("/curatoryTrac1", "curatorTrac".getBytes()).and()
//                .setData().forPath("/curatoryTrac1", "baidu".getBytes()).and().commit();
//
//        for (CuratorTransactionResult result : results) {
//            System.out.println(result.getForPath() + "->" + result.getType()+"->"+result.getResultStat().toString());
//        }
    }
}
