package com.drafire.distributed.rabbitmq.topic;

public class EmitTopicReceiver3 {
    public static void main(String[] args) throws Exception {
        EmitLogTopicAction.receive("topicReceiver3", "fruit.*");
    }
}
