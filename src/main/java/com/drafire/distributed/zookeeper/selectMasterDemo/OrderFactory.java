package com.drafire.distributed.zookeeper.selectMasterDemo;

import org.apache.curator.framework.CuratorFramework;

/**
 * 订单工厂
 */
public interface OrderFactory {
    void selectMaster(DefaultOrderPublisher publisher, CuratorFramework curatorFramework, Order order);
    void stopSelectMaster(Order order);
}
