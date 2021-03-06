package com.drafire.distributed.zookeeper.selectMasterDemo;

import com.drafire.distributed.zookeeper.curatorDemo.CuratorHelper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 发布者
 */
public class DefaultOrderPublisher implements OrderPublisher {

    /**
     * 订阅者列表
     */
    private List<Order> list;

    /**
     * 用于执行同时选举的线程池。用Thread 会带来一些线程状态的问题，用线程池可以避免这些问题
     */
    private ExecutorService orderThreadPool;

    /**
     * 被选举成为master的订单
     */
    private Order masterOrder;

    /**
     * curator客户端
     */
    private CuratorFramework curatorFramework;

    /**
     * 是否正在选举master标志
     */
    private boolean isSelectingMaster = true;

    public DefaultOrderPublisher() {
        this.list = new ArrayList<>();
        this.orderThreadPool = Executors.newCachedThreadPool();
        this.curatorFramework = CuratorHelper.getInstance();
    }

    @Override
    public void register(Order order) {
        list.add(order);
    }

    @Override
    public void remove(Order order) {
        list.remove(order);
    }

    @Override
    public void notifySelectMaster() {
        //这里先建立一个父的node，并且建立子节点的监听事件
        Stat stat;
        try {
            PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, NodeHelper.PARENT_NODE, true);
            childrenCache.start(PathChildrenCache.StartMode.NORMAL);

            //监听子节点的增加、移除事件
            childrenCache.getListenable().addListener((CuratorFramework cf, PathChildrenCacheEvent event) -> {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        notifyStopSelectMaster();
                        break;
                    case CHILD_REMOVED:
                        System.out.println("监听到master被删除");
                        doSelect();
                        break;
                }
            });

            stat = curatorFramework.checkExists().forPath(NodeHelper.PARENT_NODE);
            if (stat == null) {
                curatorFramework.create().forPath(NodeHelper.PARENT_NODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        doSelect();

    }

    public void doSelect() {
        //多线程选举
        for (Order order : list) {
            orderThreadPool.execute(() -> {
                System.out.println("线程[" + order.getName() + "]正在执行选举master");
                order.selectMaster(this, curatorFramework, order);
            });
        }
    }

    @Override
    public void notifyStopSelectMaster() {
        for (Order order : list
                ) {
            order.stopSelectMaster(order);
        }
    }

    public void setMaster(Order order) {
        this.masterOrder = order;
    }

    public void removeMaster() {
        this.masterOrder = null;
        Stat stat = null;
        try {
            curatorFramework.delete().forPath(NodeHelper.MASTER_NODE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Order getMasterOrder() {
        return masterOrder;
    }

    public CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }

    public boolean isSelectingMaster() {
        return isSelectingMaster;
    }

    public void setSelectingMaster(boolean selectingMaster) {
        isSelectingMaster = selectingMaster;
    }
}
