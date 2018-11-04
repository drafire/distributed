package com.drafire.distributed.zookeeper.selectMasterDemo;

import org.apache.curator.framework.CuratorFramework;

/**
 * 订单工厂
 */
public interface OrderFactory {
    /**
     * 选举master
     * @param publisher 发布机构
     * @param curatorFramework curator 客户端
     * @param order 订单
     */
    void selectMaster(DefaultOrderPublisher publisher, CuratorFramework curatorFramework, Order order);

    /**
     * 停止选举master
     * @param order
     */
    void stopSelectMaster(Order order);
}
