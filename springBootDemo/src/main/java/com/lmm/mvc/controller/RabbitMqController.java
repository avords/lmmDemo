package com.lmm.mvc.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

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

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> responseEntity =  restTemplate.exchange("http://www.baidu.com", HttpMethod.POST,HttpEntity.EMPTY,String.class);
        String body = responseEntity.getBody();
        System.out.println(body);
    }
}
