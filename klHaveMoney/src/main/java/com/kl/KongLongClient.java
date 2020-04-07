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
import java.util.Random;
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
    private static final ThreadPoolExecutor executorGrowUp = new ThreadPoolExecutor(2, 2, 120, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

    private static final AtomicInteger count = new AtomicInteger(0);
    private static final RetryTemplate retryTemplate = KongLongRetry.getRetryTemplate();

    public static void start() throws Exception {

        growUp();//每日增长

        lookVideo();//看视频
    }

    public static void lookVideo() throws Exception {
        Random r = new Random();
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
                        TimeUnit.SECONDS.sleep(30 + r.nextInt(10));
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

    public static void growUp() throws Exception {
        executorGrowUp.execute(() -> {
            String token1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJkZXYiLCJpYXQiOjE1ODYxNDMwMjIsImV4cCI6MTU4ODczNTAyMiwibmJmIjoxNTg2MTQzMDIyLCJ1aWQiOjI1ODcxMDcyfQ.WN6p8yuufpSPmaEBfljGZpN-8i6USqvwvd_AfU5WwfI";
            String token2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJkZXYiLCJpYXQiOjE1ODYxNzAzMzAsImV4cCI6MTU4ODc2MjMzMCwibmJmIjoxNTg2MTcwMzMwLCJ1aWQiOjI2MTgxNTQzfQ.IgZ9EuB3jOCH7Eg2aDO0aN6OHRNfqs5_kNYpJI9D2Dg";
            String ip1 = IPUtils.generateIP();
            String ip2 = IPUtils.generateIP();

            try {
                retryTemplate.execute(retryContext -> KongLongUtils.daySign(token1, ip1));
            } catch (Exception e) {
                logger.error("token1 day sign error!", e);
            }

            try {
                retryTemplate.execute(retryContext -> KongLongUtils.daySign(token2, ip2));
            } catch (Exception e) {
                logger.error("token2 day sign error!", e);
            }

            for (int i = 0; i < 5 * 60 * 60 / 2; i++) {

                try {
                    KongLongUtils.syncTime(token1, ip1);
                    TimeUnit.MILLISECONDS.sleep(50);
                    KongLongUtils.syncTime(token2, ip2);
                } catch (Exception e) {
                    logger.error("sync time have error!", e);
                }

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    logger.error("syncTime sleep have error", e);
                }
                logger.info("sync time success===========================");
            }
        });
    }

    public static void main(String[] args) throws Exception {
        start();
    }
}
