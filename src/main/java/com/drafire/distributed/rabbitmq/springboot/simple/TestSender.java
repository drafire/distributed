package com.drafire.distributed.rabbitmq.springboot.simple;

import com.drafire.distributed.rabbitmq.springboot.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSender {

    @Autowired
    private Producer producer;

    @Autowired
    private Consumer consumer;

    @Test
    public void send() {
        producer.send("又出国了");
    }

    @Test
    public void sendUser() {
        User user = new User("张三", 18);
        producer.sendUser(user);
    }
}
