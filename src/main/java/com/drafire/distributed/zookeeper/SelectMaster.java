package com.drafire.distributed.zookeeper;

public class SelectMaster {
    //1、多线程，每个线程都尝试建立同一个节点（while(true){ xxxxx }），用作master，如：/OrderMaster
    //2、多个线程竞争的时候，只有一个节点建立成功，保存节点的名称，并且建立一个watcher，用于节点被删除的时候，其他线程暂停竞争master
    //3、当节点被删除的时候，触发方法，通知其他节点，开始竞争master
}
