package com.lmm.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/5/25.
 */
public class ThreadMaxNumDemo1 {
    private static AtomicInteger i = new AtomicInteger(0);
    public static void main(String[] args) {
        for (int index=0;index<100000;index++){
            Thread t = new Thread(){
                @Override
                public void run() {
                    try {
                        int t = i.incrementAndGet();
                        System.out.println("第"+t+"个线程开始休眠");
                        Thread.sleep(30000);
                        System.out.println("第"+t+"个线程被唤醒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
    }
}