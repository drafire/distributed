package com.drafire.distributed.rabbitmq.springboot.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutReceiver {
    @RabbitListener(queues = "queue_a")
    public void processA(String msg) {
        System.out.println("queue_A receive:" + msg);
    }

    @RabbitListener(queues = "queue_b")
    public void processB(String msg) {
        System.out.println("queue_B receive:" + msg);
    }

    @RabbitListener(queues = "queue_c")
    public void processC(String msg) {
        System.out.println("queue_C receive:" + msg);
    }
}
