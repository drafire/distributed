package com.drafire.distributed.zookeeper.curatorDemo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;

public class EventTest {

    /**
     * 三种watcher来做节点的监听
     * pathcache   监视一个路径下子节点的创建、删除、节点数据更新
     * NodeCache   监视一个节点的创建、更新、删除
     * TreeCache   pathcaceh+nodecache 的合体（监视路径下的创建、更新、删除事件），
     * 缓存路径下的所有子节点的数据
     */

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework= CuratorHelper.getCuratorFramework();

//        NodeCache cache=new NodeCache(curatorFramework,"/curatoryTrac");
//        cache.start(true);
//        cache.getListenable().addListener(()->{
//            System.out.println("节点curatorTrac发生了变化，变化，结果如下："+cache.getCurrentData());
//        });
//
//        curatorFramework.setData().forPath("/curatoryTrac");
//        //不能监听子节点的创建
//        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/curatoryTrac/curatoryTrac2");
//
//        //也不能监听节点创建。也很容易理解，节点都还没创建，如何监听
//        NodeCache cache1=new NodeCache(curatorFramework,"/curatory3");
//        cache1.start(true);
//        cache1.getListenable().addListener(()->{
//            System.out.println("curatory3，创建了，结果如下："+cache.getCurrentData());
//        });
//
//        curatorFramework.create().forPath("/curatory3");

        PathChildrenCache childrenCache=
                new PathChildrenCache(curatorFramework,"/curatoryTrac1",true);
        childrenCache.start(PathChildrenCache.StartMode.NORMAL);

        childrenCache.getListenable().addListener((CuratorFramework cf, PathChildrenCacheEvent pcc)->{
            switch (pcc.getType()){
                case CHILD_REMOVED:
                    System.out.println(cf.getData()+"->被移除了");
                    break;
                case CHILD_ADDED:
                    System.out.println(cf.getData()+"->增加了");
                    break;
                case CHILD_UPDATED:
                    System.out.println(cf.getData()+"->修改了");
                    break;
            }
        });

//        curatorFramework.create().forPath("/curatoryTrac10");
//        TimeUnit.SECONDS.sleep(3);

//        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/curatoryTrac11");
//        TimeUnit.SECONDS.sleep(3);

//        curatorFramework.setData().forPath("/curatoryTrac10","10".getBytes());
//        TimeUnit.SECONDS.sleep(3);

//        curatorFramework.create().forPath("/curatoryTrac12");
//        TimeUnit.SECONDS.sleep(3);

        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/curatoryTrac1");

    }
}
