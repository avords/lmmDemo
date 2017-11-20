package com.lmm.dbDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2016/12/30.
 */
public class ThreadDemo implements Callable<String> {
    private int number;
    private CountDownLatch countDownLatch;
    public ThreadDemo(int i, CountDownLatch countDownLatch) {
        this.number = i;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public String call() throws Exception {
        try {
            System.out.println("我" + number + "开始睡觉了！");
            if (number % 10 == 0) {
                throw new RuntimeException("我出异常了");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("谁打扰我：" + number + "了！");
                e.printStackTrace();
            }
            System.out.println("我" + number + "睡觉结束了！");
        }finally {
            countDownLatch.countDown();
        }
        return "sucess";
    }
}
