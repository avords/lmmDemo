package com.lmm.springdemo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/9/28.
 */
public class Runer implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runer.class);
    private int index;
    public Runer() {
    }
    public Runer(int i) {
        this.index = i;
    }
    @Override
    public void run() {
        LOGGER.info("我是线程"+Thread.currentThread().getName()+"下标为："+index);
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
