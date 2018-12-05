package com.drafire.distributed.rabbitmq.topic;

public class EmitTopicReceiver1 {
    public static void main(String[] args) throws Exception {
        EmitLogTopicAction.receive("topicReceiver1", "orange.*.*");
    }
}
