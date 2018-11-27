package com.lmm.disruptorDemo;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmm.disruptorDemo.even.LongEvent;
import com.lmm.disruptorDemo.factory.LongEventFactory;
import com.lmm.disruptorDemo.factory.MyThreadFactory;
import com.lmm.disruptorDemo.translator.MyTranslator;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * Created by arno.yan on 2018/11/27.
 */
public class disruptorTest1 {

    private static final MyTranslator TRANSLATOR = new MyTranslator();

    public static void main(String[] args) throws InterruptedException {
        EventHandler handler1 = (EventHandler<LongEvent>) (event, sequence, endOfBatch) -> System.out.println("handler1 : " + event.getValue());
        EventHandler handler2 = (EventHandler<LongEvent>) (event, sequence, endOfBatch) -> System.out.println("handler2 : " + event.getValue());

        EventHandler handler3 = (EventHandler<LongEvent>) (event, sequence, endOfBatch) -> System.out.println("handler3 : " + event.getValue() +
                " arrived. Handler1 and handler2 should have completed. Completed.\n");

        //构造Disruptor
        EventFactory<LongEvent> eventFactory = new LongEventFactory();
        int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；
        Disruptor<LongEvent> disruptor = new Disruptor<>(eventFactory,
                ringBufferSize, new MyThreadFactory(), ProducerType.SINGLE,
                new YieldingWaitStrategy());

        //1,2完成了，才完成3
        disruptor.handleEventsWith(handler1, handler2).then(handler3);

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; l < 1000; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent(TRANSLATOR, bb);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
