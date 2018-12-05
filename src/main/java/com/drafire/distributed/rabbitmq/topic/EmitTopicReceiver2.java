package com.drafire.distributed.rabbitmq.topic;

public class EmitTopicReceiver2 {
    public static void main(String[] args) throws Exception {
        EmitLogTopicAction.receive("topicReceiver1", "*.#.apple");
    }
}
