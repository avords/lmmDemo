package com.lmm.utils;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: arno.yan
 * @Date: 2019/3/22
 */
public class ThreadPoolExecutorTest {
    
    private static final ThreadPoolExecutor executors = new ThreadPoolExecutor(10,100,120, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));
    
    public static void main(String[] args) throws IOException {
        for(int i=0;i<31;i++){
            final int s = i;
            executors.submit(()->{
                System.out.println("线程"+s+"执行");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
