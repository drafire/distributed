package com.drafire.distributed.rabbitmq.workerqueues;

import com.drafire.distributed.rabbitmq.RabbitMQHelper;
import com.rabbitmq.client.*;

import java.util.concurrent.TimeUnit;

public class Worker1 {
    public static void main(String[] args) throws Exception {
        WorkerAction.receive(Worker1.class.getName());
    }
}
