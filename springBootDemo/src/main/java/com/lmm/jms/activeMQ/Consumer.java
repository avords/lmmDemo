package com.lmm.jms.activeMQ;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/12/6.
 */
@Component
public class Consumer {
    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息  
    @JmsListener(destination = "mytest.queue")
    public void receiveQueue(String text) {
        System.out.println("Consumer收到的报文为:"+text);
    }

    @JmsListener(destination = "mytest.topic")
    public void topic1(String text) {
        System.out.println("Consumer收到的报文为:"+text);
    }
    @JmsListener(destination = "mytest.topic")
    public void topic2(String text) {
        System.out.println("Consumer收到的报文为:"+text);
    }
}  
