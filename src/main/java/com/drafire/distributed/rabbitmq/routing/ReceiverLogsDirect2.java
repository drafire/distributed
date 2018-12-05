package com.drafire.distributed.rabbitmq.routing;

/**
 * 发布/订阅 模式，使用direct模式
 */
public class ReceiverLogsDirect2 {

    public static void main(String[] args) throws Exception {
        RoutingAction.receive("ReceiverLogsDirect2","error");
    }
}
