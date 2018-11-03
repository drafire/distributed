package com.drafire.distributed.zookeeper.selectMasterDemo;

import java.util.Random;

public class SelectMasterTest {
    //1、多线程，每个线程都尝试建立同一个节点（while(true){ xxxxx }），用作master，如：/OrderMaster
    //2、多个线程竞争的时候，只有一个节点建立成功，保存节点的名称，并且建立一个watcher，用于节点被删除的时候，其他线程暂停竞争master
    //3、当节点被删除的时候，触发方法，通知其他节点，开始竞争master

    public static void main(String[] args) throws InterruptedException {
        DefaultOrderPublisher publisher = new DefaultOrderPublisher();

        Order order1 = new Order("苹果");
        Order order2 = new Order("雪梨");
        Order order3 = new Order("桃子");
        Order order4 = new Order("香蕉");
        Order order5 = new Order("柚子");
        Order order6 = new Order("荔枝");
        Order order7 = new Order("龙眼");

        publisher.register(order5);
        publisher.register(order6);
        publisher.register(order7);
        publisher.register(order1);
        publisher.register(order2);
        publisher.register(order3);
        publisher.register(order4);

        publisher.notifySelectMaster();

        while (true) {
            Thread.sleep(1000 * 2);

            if (!publisher.isSelectingMaster()) {
                Random random = new Random();
                int num = random.nextInt(100);
                String masterName = publisher.getMasterOrder().getName();
                //设置25% master 故障率
                if (num >= 75 && publisher.getMasterOrder() != null) {
                    System.out.println("执行移除master[" + masterName + "]");
                    publisher.removeMaster();
                }
            }
        }
    }

}
