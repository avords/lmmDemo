package com.lmm.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class CommandDefaultFailure extends HystrixCommand<String> {

    private final String name;

    //在10s内，如果请求在5个及以上，且有50%失败的情况下，开启断路器；断路器开启1000ms后尝试关闭
    public CommandDefaultFailure(String name) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))  //必须
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(50)//超时时间
                        .withCircuitBreakerRequestVolumeThreshold(5)
                        .withCircuitBreakerSleepWindowInMilliseconds(1000)
                        .withCircuitBreakerErrorThresholdPercentage(50))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ExampleGroup-pool"))  //可选,默认 使用 this.getClass().getSimpleName();
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(4)));

        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return null;
    }

    @Override
    protected String getFallback() {
        return super.getFallback();
    }
}
