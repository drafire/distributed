package com.drafire.distributed.rabbitmq.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    private final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);
}

