package com.lmm.jms.rabbitMQ;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class ExchangeAndQueueReceive {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeAndQueueReceive.class);

    /**
     * 监听替补队列 来验证死信.
     *
     * @param message the message
     * @param channel the channel
     * @throws IOException the io exception  这里异常需要处理
     */
    @RabbitListener(queues = {"REDIRECT_QUEUE"})
    public void redirect(String str, Message message, Channel channel){
        logger.info("消费时间：{}，消费内容：{}",LocalDateTime.now(),str);
        try {
            //TODO 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            //TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }

}
