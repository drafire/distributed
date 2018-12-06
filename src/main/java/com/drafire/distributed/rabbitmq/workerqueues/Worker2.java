package com.drafire.distributed.rabbitmq.workerqueues;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.concurrent.TimeUnit;

public class Worker2 {
    public static void main(String[] args) throws Exception {
       WorkerAction.receive("workder2");
    }

}
