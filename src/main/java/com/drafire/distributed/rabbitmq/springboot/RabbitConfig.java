package com.drafire.distributed.rabbitmq.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class RabbitConfig {
    private final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    private static final String EXCHANGE_A="exchange_a";
    private static final String EXCHANGE_B="exchange_b";
    private static final String EXCHANGE_C="exchange_c";

    private static final String QUEUE_A="queue_a";
    private static final String QUEUE_B="queue_b";
    private static final String QUEUE_C="queue_c";

    public static final String ROUTINGKEY_A = "routingKey_a";
    public static final String ROUTINGKEY_B = "routingKey_b";
    public static final String ROUTINGKEY_C = "routingKey_c";

    @Bean
    public ConnectionFactory getConnectionFactory(){
        CachingConnectionFactory factory= new CachingConnectionFactory(host,port);
        factory.setUsername(this.userName);
        factory.setPassword(this.password);
        factory.setVirtualHost("/");
        factory.setPublisherConfirms(true);
        return factory;
    }

    @Bean
    //必须是prototype类型
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate getRebbitTemplate(){
        RabbitTemplate template=new RabbitTemplate(getConnectionFactory());
        return template;
    }
}
