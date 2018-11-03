package com.drafire.distributed.zookeeper.selectMasterDemo;

public interface OrderPublisher {
    void register(Order order);
    void remove(Order order);
    void notifySelectMaster();
    void notifyStopSelectMaster();
}
