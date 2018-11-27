package com.lmm.disruptorDemo.factory;

import java.util.concurrent.ThreadFactory;

/**
 * Created by arno.yan on 2018/11/27.
 */
public class MyThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
