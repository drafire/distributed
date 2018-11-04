package com.drafire.distributed.zookeeper.selectMasterDemo;

import org.apache.curator.framework.CuratorFramework;

/**
 * 订单工厂默认实现
 */
public class DefaultOrderFactory implements OrderFactory {

    @Override
    public void selectMaster(DefaultOrderPublisher publisher, CuratorFramework curatorFramework, Order order) {

        if (publisher.getMasterOrder() != null) {
            System.err.println("[" + order.getName() + "]->竞争master失败");
        }
        try {
            curatorFramework.create().forPath(NodeHelper.MASTER_NODE, order.getName().getBytes());
            publisher.setMaster(order);
            System.out.println("[" + order.getName() + "]->竞争到了master");
            publisher.setSelectingMaster(false);
        } catch (Exception e) {
            System.err.println("[" + order.getName() + "]->竞争master失败");
        }
    }

    @Override
    public void stopSelectMaster(Order order) {
        System.err.println(order.getName() + "收到了停止选举master通知");
    }
}
