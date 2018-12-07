package com.drafire.distributed.rabbitmq.springboot.simple;

import com.drafire.distributed.rabbitmq.springboot.dto.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(queues = "queue_a")
    public void process(String msg) {
        System.out.println("接收到：" + msg);
    }

    @RabbitListener(queues = "queue_a")
    public void getUser(User user) {
        System.out.println("客户端收到：" + user.toString());
    }
}
