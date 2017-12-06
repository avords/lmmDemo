package com.lmm.jms.activeMQ;

import com.lmm.mvc.controller.SampleController;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

/**
 * Created by Administrator on 2017/12/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SampleController.class)
public class SpringbootJmsApplicationTests {

    @Autowired
    private Producer producer;

    @Test
    public void queueSend() throws InterruptedException {
        Destination destination = new ActiveMQQueue("mytest.queue");

        for(int i=0; i<100; i++){
            producer.sendMessage(destination, "myname is chhliu!!!");
        }
    }
    @Test
    public void topicSend() throws InterruptedException {
        Destination destination = new ActiveMQTopic("mytest.topic");

        for(int i=0; i<100; i++){
            producer.sendMessage(destination, "myname is chhliu!!!");
        }
    }
}  
