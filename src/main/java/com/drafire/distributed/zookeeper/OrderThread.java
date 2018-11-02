package com.drafire.distributed.zookeeper;

public class OrderThread implements Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("线程["+name+"]正在执行选举master");
        order.selectMaster();
    }

    private Order order;
    private String name;

    public OrderThread(Order order,String name) {
        this.order = order;
        this.name=name;
    }
}
