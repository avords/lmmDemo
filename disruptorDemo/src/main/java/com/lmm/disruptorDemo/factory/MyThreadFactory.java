package com.lmm.disruptorDemo.factory;

/**
 * Created by arno.yan on 2018/11/27.
 */
public class MyThreadFactory implements java.util.concurrent.ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
