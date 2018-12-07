package com.drafire.distributed.rabbitmq.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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

    private static final String EXCHANGE_A = "exchange_a";
    private static final String EXCHANGE_B = "exchange_b";
    private static final String EXCHANGE_C = "exchange_c";

    private static final String TOPIC_QUEUE_A = "queue_a";
    private static final String TOPIC_QUEUE_B = "queue_b";
    private static final String TOPIC_QUEUE_C = "queue_c";

    private static final String ROUTINGKEY_A = "routingKey_a.*";
    private static final String ROUTINGKEY_B = "routingKey_b.#";
    private static final String ROUTINGKEY_C = "routingKey_c";

    @Bean
    public ConnectionFactory getConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(host, port);
        factory.setUsername(this.userName);
        factory.setPassword(this.password);
        factory.setVirtualHost("/");
        factory.setPublisherConfirms(true);
        return factory;
    }

    @Bean(name = TOPIC_QUEUE_A)
    public Queue queueA() {
        return new Queue(TOPIC_QUEUE_A);
    }

    @Bean(name = TOPIC_QUEUE_B)
    public Queue queueB() {
        return new Queue(TOPIC_QUEUE_B);
    }

    @Bean(name = TOPIC_QUEUE_C)
    public Queue queueC() {
        return new Queue(TOPIC_QUEUE_C);
    }

    @Bean
    public TopicExchange getExchange() {
        return new TopicExchange(EXCHANGE_A);
    }

    /**
     * topic模式
     * @param queueA
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingTopicExchangeQueueA(@Qualifier(TOPIC_QUEUE_A) Queue queueA, TopicExchange exchange) {
        return BindingBuilder.bind(queueA).to(exchange).with(ROUTINGKEY_A);
    }

    @Bean
    Binding bindingTopicExchangeQueueB(@Qualifier(TOPIC_QUEUE_B) Queue queueB, TopicExchange exchange) {
        return BindingBuilder.bind(queueB).to(exchange).with(ROUTINGKEY_B);
    }

    /**
     * fanout模式
     *
     * @return
     */
    @Bean
    FanoutExchange getFanoutExchange() {
        return new FanoutExchange(EXCHANGE_B);
    }

    @Bean
    Binding bindingFanoutExchangeQueueA(@Qualifier(TOPIC_QUEUE_A) Queue queueA, FanoutExchange exchange) {
        return BindingBuilder.bind(queueA).to(exchange);   //因为是fanout模式，不需要with了
    }

    @Bean
    Binding bindingFanoutExchangeQueueB(@Qualifier(TOPIC_QUEUE_B) Queue queueB, FanoutExchange exchange) {
        return BindingBuilder.bind(queueB).to(exchange);   //因为是fanout模式，不需要with了
    }

    @Bean
    Binding bindingFanoutExchangeQueueC(@Qualifier(TOPIC_QUEUE_C) Queue queueC, FanoutExchange exchange) {
        return BindingBuilder.bind(queueC).to(exchange);   //因为是fanout模式，不需要with了
    }

    public static String getExchangeA() {
        return EXCHANGE_A;
    }

    public static String getExchangeB() {
        return EXCHANGE_B;
    }

    public static String getExchangeC() {
        return EXCHANGE_C;
    }

    public static String getQueueA() {
        return TOPIC_QUEUE_A;
    }

    public static String getTopicQueueB() {
        return TOPIC_QUEUE_B;
    }

    public static String getQueueC() {
        return TOPIC_QUEUE_C;
    }

    public static String getRoutingkeyA() {
        return ROUTINGKEY_A;
    }

    public static String getRoutingkeyB() {
        return ROUTINGKEY_B;
    }

    public static String getRoutingkeyC() {
        return ROUTINGKEY_C;
    }


}
