package com.lmm.jms.test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/25.
 */
public class TopicSubscriber {
    public static final String user = "system";
    public static final String password = "manager";
    public static final String url = "tcp://localhost:61616";

    public static void main(String[] args) throws Exception {
        // ConnectionFactory ：连接工厂，JMS 用它创建连接  
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        // Connection ：JMS 客户端到JMS Provider 的连接  
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // Session： 一个发送或接收消息的线程  
        final Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        // Destination ：消息的目的地;消息发送给谁.  
        Topic topic = session.createTopic("ydqTopic");
        // 消费者，消息接收者  
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {//有事务限制  
            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("消费者1【"+sdf.format(new Date())+"】："+textMessage.getText());
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }
        });
        MessageConsumer comsumer2 = session.createConsumer(topic);
        comsumer2.setMessageListener(new MessageListener(){
            public void onMessage(Message message) {
                TextMessage tm = (TextMessage) message;
                try {
                    System.out.println("消费者2【"+sdf.format(new Date())+"】：" + tm.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
