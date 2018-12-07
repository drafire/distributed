package com.drafire.distributed.rabbitmq.springboot.simple;

import com.drafire.distributed.rabbitmq.springboot.RabbitConfig;
import com.drafire.distributed.rabbitmq.springboot.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    private final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String worlds) {
        amqpTemplate.convertAndSend("queue_a", "hello," + worlds);
    }

    public void sendUser(User user) {
        amqpTemplate.convertAndSend("queue_a", user);
    }

}

