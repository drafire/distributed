package com.drafire.distributed.zookeeper.curatorDemo.LeaderSelectorDemo;

import com.drafire.distributed.zookeeper.curatorDemo.CuratorHelper;
import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.utils.CloseableUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 这个类用来测试curator的选举leader的功能
 */
public class LeaderSelectorDemoTest {

    private static final String LATCH_PATH = "/Leader";
    private static final int CLIENT_QTY = 10;

    public static void main(String[] args) {
        selectUsingLeaderLatch();
    }

    /**
     * 使用LeaderLatch算法
     */
    private static void selectUsingLeaderLatch() {
        List<CuratorFramework> cfs = Lists.newArrayList();   //也是封装了new ArrayList();
        List<LeaderLatch> lls = new ArrayList<>();

        try {
            for (int i = 0; i < CLIENT_QTY; i++) {
                CuratorFramework curatorFramework = CuratorHelper.getCuratorFramework();
                cfs.add(curatorFramework);
                LeaderLatch latch = new LeaderLatch(curatorFramework, LATCH_PATH, "Client #" + i);
                lls.add(latch);
                //开始选举
                latch.start();
            }

            TimeUnit.SECONDS.sleep(12);

            //查找leader
            LeaderLatch currentLeader = null;
            for (LeaderLatch item : lls) {
                if (item.hasLeadership()) {
                    currentLeader = item;
                }
            }

            System.out.println("current leader is " + currentLeader.getId());
            System.out.println("release the leader " + currentLeader.getId());

            //结束选举
            currentLeader.close();

            System.out.println("Press enter/return to quit\n");

            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (CuratorFramework item : cfs) {
                CloseableUtils.closeQuietly(item);
            }

            for (LeaderLatch item : lls) {
                CloseableUtils.closeQuietly(item);
            }
        }
    }
}
