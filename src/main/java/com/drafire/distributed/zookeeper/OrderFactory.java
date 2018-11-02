package com.drafire.distributed.zookeeper;

/**
 * 订单工厂
 */
public interface OrderFactory {
    void selectMaster();
    void stopSelectMaster();
}
