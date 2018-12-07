package com.drafire.distributed.rabbitmq.springboot.fanout;

import com.drafire.distributed.rabbitmq.springboot.RabbitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FanoutTestSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void send() {
        //这里routingKey 不会起作用，因为fanout是广播形式
        amqpTemplate.convertAndSend(RabbitConfig.getExchangeB(), "", "fanout模式消息" + LocalDateTime.now().toString());
    }

}
