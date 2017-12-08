package com.lmm.boot.config;

import java.util.List;

/**
 * Created by Administrator on 2017/12/8.
 */
public class ConsumerConfig {
    private String instanceName;
    private List<String> subscribe;

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public List<String> getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(List<String> subscribe) {
        this.subscribe = subscribe;
    }
}
