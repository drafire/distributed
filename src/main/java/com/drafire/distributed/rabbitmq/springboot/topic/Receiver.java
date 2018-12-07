package com.drafire.distributed.rabbitmq.springboot.topic;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    @RabbitListener(queues = "queue_a")
    public void processA(String msg) {
        System.out.println("queue_A receive:" + msg);
    }

    @RabbitListener(queues = "queue_b")
    public void processB(String msg) {
        System.out.println("queue_B receive:" + msg);
    }
}
