package com.drafire.distributed.zookeeper.selectMasterDemo;

import com.drafire.distributed.zookeeper.curatorDemo.CuratorHelper;
import org.apache.curator.framework.CuratorFramework;

/**
 * 删除master测试类
 */
public class DeleteMasterTest {
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework= CuratorHelper.getInstance();
        curatorFramework.delete().deletingChildrenIfNeeded().forPath(NodeHelper.PARENT_NODE);
    }
}
