package com.lmm.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.lmm.boot.config.RocketmqProperties.PREFIX;

/**
 * Created by Administrator on 2017/12/8.
 */
@Data
@ConfigurationProperties(PREFIX)
public class RocketmqProperties {

    public static final String PREFIX = "spring.extend.rocketmq";

    private String namesrvAddr;
    private String instanceName;
    private String clientIP;
    private ProducerConfig producer;
    private ConsumerConfig consumer;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public ProducerConfig getProducer() {
        return producer;
    }

    public void setProducer(ProducerConfig producer) {
        this.producer = producer;
    }

    public ConsumerConfig getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerConfig consumer) {
        this.consumer = consumer;
    }
}  
