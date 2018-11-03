package com.drafire.distributed.zookeeper.selectMasterDemo;

import com.drafire.distributed.zookeeper.curatorDemo.ClientUnit;
import org.apache.curator.framework.CuratorFramework;

public class DeleteMasterTest {
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework=ClientUnit.getInstance();
        curatorFramework.delete().deletingChildrenIfNeeded().forPath(NodeHelper.PARENT_NODE);
    }
}
