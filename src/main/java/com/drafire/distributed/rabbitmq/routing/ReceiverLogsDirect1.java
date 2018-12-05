package com.drafire.distributed.rabbitmq.routing;

/**
 * 发布/订阅 模式，使用direct模式
 */
public class ReceiverLogsDirect1 {

    public static void main(String[] args) throws Exception {
        RoutingAction.receive("ReceiverLogsDirect1","warning");
    }
}
