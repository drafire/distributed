package com.drafire.distributed.zookeeper;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布者
 */
public class DefaultOrderPublisher implements OrderPublisher {

    private List<Order> list;
    private List<Thread> threadList;

    public DefaultOrderPublisher() {
        this.list = new ArrayList<>();
        this.threadList=new ArrayList<>();
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
        //多线程选举
        for (int i = 0; i < list.size(); i++) {
            OrderThread orderThread = new OrderThread(list.get(i), String.valueOf(i));
            Thread thread = new Thread(orderThread);
            threadList.add(thread);
        }
        for (Thread thread:threadList
             ) {
            thread.start();
        }
    }

    @Override
    public void notifyStopSelectMaster() {
        for (Order order : list
                ) {
            order.stopSelectMaster();
        }
    }
}
