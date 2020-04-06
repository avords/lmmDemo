package com.kl.util;

import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collections;

/**
 * @author: arno.yan
 * @Date: 2020/4/6
 */
public class KongLongRetry {

    public static RetryTemplate getRetryTemplate(){

        final RetryTemplate retryTemplate = new RetryTemplate();

        final SimpleRetryPolicy policy = new SimpleRetryPolicy(3, Collections.<Class<? extends Throwable>, Boolean>
                singletonMap(Exception.class, true));

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();

        //设置重试间隔时间
        fixedBackOffPolicy.setBackOffPeriod(1000);

        retryTemplate.setRetryPolicy(policy);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        return retryTemplate;
    }
}
