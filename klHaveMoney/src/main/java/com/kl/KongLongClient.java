package com.kl;

import com.kl.util.IPUtils;
import com.kl.util.KongLongRetry;
import com.kl.util.KongLongUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.support.RetryTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: arno.yan
 * @Date: 2020/4/6
 */
public class KongLongClient {

    private static final Logger logger = LoggerFactory.getLogger(KongLongClient.class);

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 120, TimeUnit.SECONDS, new ArrayBlockingQueue<>(300));
    private static final AtomicInteger count = new AtomicInteger(0);
    private static final RetryTemplate retryTemplate = KongLongRetry.getRetryTemplate();

    public static void main(String[] args) throws Exception {
        InputStream inputStream = KongLongClient.class.getClassLoader().getResourceAsStream("account.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] account = line.split("----");
            String token = account[1];
            executor.execute(() -> {
                int s = count.addAndGet(1);
                int balance = 0;
                String ip = IPUtils.generateIP();
                try {
                    balance = retryTemplate.execute(retryContext -> KongLongUtils.queryBalance(token, ip));
                    retryTemplate.execute(retryContext -> KongLongUtils.getRewards(token, ip));
                } catch (Exception e) {
                    logger.error("task:{}, token:{}, ip:{} have error", s, token, ip, e);
                }
                logger.info("task:{}, token:{}, ip:{}, balance:{}", s, token, ip, balance);
                int lastVideoAwardNum = retryTemplate.execute(retryContext -> KongLongUtils.lookVideo(token, ip));
                while (lastVideoAwardNum > 0) {
                    try {
                        TimeUnit.SECONDS.sleep(30);
                        logger.info("task:{}, token:{}, ip:{}, lastVideoAwardNum:{}", s, token, ip, lastVideoAwardNum);
                    } catch (InterruptedException e) {
                        logger.error("Thread sleep error!", e);
                    }
                    lastVideoAwardNum = retryTemplate.execute(retryContext -> KongLongUtils.lookVideo(token, ip));
                }


            });

            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
