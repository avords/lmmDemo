package com.lmm.mvc.controller;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping("/rabbit")
public class RabbitMqController {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @RequestMapping("/send")
    @ResponseBody
    public String sendTest(){
        rabbitTemplate.convertAndSend("fanoutExchange","","hello word");
        return "success";
    }

    /**
     * 测试死信队列.
     *
     * @param message the message
     * @return the response entity
     */
    @RequestMapping("/dead")
    @ResponseBody
    public String deadLetter(String message) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //声明消息处理器  这个对消息进行处理  可以设置一些参数   对消息进行一些定制化处理   我们这里  来设置消息的编码  以及消息的过期时间  因为在.net 以及其他版本过期时间不一致   这里的时间毫秒值 为字符串
        MessagePostProcessor messagePostProcessor = ms -> {
            MessageProperties messageProperties = ms.getMessageProperties();
            //设置编码
            messageProperties.setContentEncoding("utf-8");
            // TODO 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            messageProperties.setExpiration("10000");
            return ms;
        };
//         向DL_QUEUE 发送消息  10*1000毫秒后过期 形成死信
        rabbitTemplate.convertAndSend("DL_EXCHANGE", "DL_KEY", message, messagePostProcessor, correlationData);
        return "success";
    }
}
