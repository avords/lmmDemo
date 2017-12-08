package com.lmm.jms.rocketMQ;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author arno
 *
 */
@RestController
public class ProducerDemo {
    private Logger logger = LoggerFactory.getLogger(ProducerDemo.class);
    @Autowired
    private DefaultMQProducer defaultProducer;

    @Autowired
    private TransactionMQProducer transactionProducer;

    @Value("${spring.extend.rocketmq.producer.topic}")
    private String producerTopic;

    @RequestMapping(value = "/sendMsg", method = RequestMethod.GET)
    public void sendMsg() {
        Message msg = new Message(producerTopic,// topic
                "TagA",// tag
                "OrderID001",// key
                ("Hello jyqlove333").getBytes());// body
        try {
            defaultProducer.send(msg,new SendCallback(){

                @Override
                public void onSuccess(SendResult sendResult) {
                    logger.info(sendResult.toString());
                    //TODO 发送成功处理
                }

                @Override
                public void onException(Throwable e) {
                    logger.error(e.getMessage());
                    //TODO 发送失败处理
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/sendTransactionMsg", method = RequestMethod.GET)
    public String sendTransactionMsg() {
        SendResult sendResult = null;
        try {
            //构造消息
            Message msg = new Message(producerTopic,// topic
                    "TagA",// tag
                    "OrderID001",// key
                    ("Hello jyqlove333").getBytes());// body

            //发送事务消息，LocalTransactionExecute的executeLocalTransactionBranch方法中执行本地逻辑
            sendResult = transactionProducer.sendMessageInTransaction(msg, (Message msg1,Object arg) -> {
                int value = 1;

                //TODO 执行本地事务，改变value的值
                //===================================================
                logger.info("执行本地事务。。。完成");
                if(arg instanceof Integer){
                    value = (Integer)arg;
                }
                //===================================================

                if (value == 0) {
                    throw new RuntimeException("Could not find db");
                } else if ((value % 5) == 0) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if ((value % 4) == 0) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }, 4);
            logger.info(sendResult.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return sendResult.toString();
    }
}