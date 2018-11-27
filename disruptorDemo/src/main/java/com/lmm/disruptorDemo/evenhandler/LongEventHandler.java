package com.lmm.disruptorDemo.evenhandler;

import com.lmax.disruptor.EventHandler;
import com.lmm.disruptorDemo.even.LongEvent;

/**
 * Created by arno.yan on 2018/11/27.
 */
public class LongEventHandler implements EventHandler<LongEvent>
{
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println("接收到Event的值为: " + event.getValue());
    }
}
