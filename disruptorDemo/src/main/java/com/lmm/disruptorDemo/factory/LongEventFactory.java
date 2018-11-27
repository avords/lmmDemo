package com.lmm.disruptorDemo.factory;

import com.lmax.disruptor.EventFactory;
import com.lmm.disruptorDemo.even.LongEvent;

/**
 * Created by arno.yan on 2018/11/27.
 */
public class LongEventFactory implements EventFactory<LongEvent>
{
    public LongEvent newInstance()
    {
        return new LongEvent();
    }
}
