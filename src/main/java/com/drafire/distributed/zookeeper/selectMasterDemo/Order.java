package com.drafire.distributed.zookeeper.selectMasterDemo;

import org.apache.curator.framework.CuratorFramework;

/**
 * 订单类
 */
public class Order extends DefaultOrderFactory {
    private String name;

    public Order(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
