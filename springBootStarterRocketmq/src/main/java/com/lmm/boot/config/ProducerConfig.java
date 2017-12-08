package com.lmm.boot.config;

/**
 * Created by Administrator on 2017/12/8.
 */
public class ProducerConfig {
    private String instanceName;
    private String tranInstanceName;
    private String topic;

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getTranInstanceName() {
        return tranInstanceName;
    }

    public void setTranInstanceName(String tranInstanceName) {
        this.tranInstanceName = tranInstanceName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
