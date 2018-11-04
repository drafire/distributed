package com.drafire.distributed.zookeeper.selectMasterDemo;

/**
 * 发布者接口
 */
public interface OrderPublisher {
    /**
     * 订阅方法
     * @param order
     */
    void register(Order order);

    /**
     * 取消订阅方法
     * @param order
     */
    void remove(Order order);

    /**
     * 通知选举master方法
     */
    void notifySelectMaster();

    /**
     * 通知停止选举master方法
     */
    void notifyStopSelectMaster();
}
