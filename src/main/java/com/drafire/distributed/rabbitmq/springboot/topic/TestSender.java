package com.drafire.distributed.rabbitmq.springboot.topic;

import com.drafire.distributed.rabbitmq.springboot.RabbitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void send1() {
        amqpTemplate.convertAndSend(RabbitConfig.getExchangeA(), "routingKey_a.hello", "队列a消息来哦了");
    }

    @Test
    public void send2() {
        //这里需要把b后面的那个.带上，否则匹配不了
        amqpTemplate.convertAndSend(RabbitConfig.getExchangeA(),"routingKey_b.", "队列b消息");
    }

    @Test
    public void send3() {
        amqpTemplate.convertAndSend(RabbitConfig.getExchangeA(),"routingKey_a.b.c", "队列b接收不到的消息");
    }
}
