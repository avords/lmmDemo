package com.lmm.disruptorDemo.translator;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmm.disruptorDemo.even.LongEvent;

import java.nio.ByteBuffer;

/**
 * Created by arno.yan on 2018/11/27.
 */
public class MyTranslator implements EventTranslatorOneArg<LongEvent, ByteBuffer> {
    @Override
    public void translateTo(LongEvent event, long sequence, ByteBuffer data) {
        event.setValue(data.getLong(0));
    }
}
