package com.lmm.disruptorDemo.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by arno.yan on 2018/11/27.
 */
public class MyThreadFactory implements ThreadFactory {

    private final AtomicInteger index = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(null, r, "disruptor-thread-" + index.getAndIncrement());
    }
}
