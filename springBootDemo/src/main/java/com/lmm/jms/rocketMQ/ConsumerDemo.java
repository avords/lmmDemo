package com.lmm.jms.rocketMQ;

import com.lmm.boot.config.RocketmqEvent;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author arno
 *
 */
@Component
public class ConsumerDemo {
    private Logger logger = LoggerFactory.getLogger(ConsumerDemo.class);

    /*@EventListener(condition = "#event.topic=='sprintBoot1' && #event.tag=='TagA'")*/
    public void rocketmqMsgListen(RocketmqEvent event) {
        DefaultMQPushConsumer consumer = event.getConsumer();
        try {
            logger.info("com.bqjr.consumerDemo监听到一个消息达到：" + event.getMsg("gbk"));
            //TODO 进行业务处理
        } catch (Exception e) {
            if(event.getMessageExt().getReconsumeTimes()<=3){//重复消费3次
                try {
                    consumer.sendMessageBack(event.getMessageExt(), 2);
                } catch (Exception e1) {
                    //TODO 消息消费失败，进行日志记录
                }
            } else {
                //TODO 消息消费失败，进行日志记录

            }
        }
    }
}