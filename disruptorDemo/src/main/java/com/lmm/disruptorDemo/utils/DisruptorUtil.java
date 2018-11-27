package com.lmm.disruptorDemo.utils;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmm.disruptorDemo.even.LongEvent;
import com.lmm.disruptorDemo.evenhandler.LongEventHandler;
import com.lmm.disruptorDemo.factory.LongEventFactory;
import com.lmm.disruptorDemo.factory.MyThreadFactory;

/**
 * Created by arno.yan on 2018/11/27.
 */
public class DisruptorUtil {

    private static Disruptor disruptor;
    
    public static void main(String[] args) {

        //启动
        Disruptor disruptor = start();

        //发布事件；
        for(long i=0;i<200;i++){
            publishEvent(disruptor, i);
        }

        //disruptor.shutdown();//关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
        //executor.shutdown();//关闭 disruptor 使用的线程池；如果需要的话，必须手动关闭， disruptor 在 shutdown 时不会自动关闭；
    }

    private static Disruptor start() {
        EventFactory<LongEvent> eventFactory = new LongEventFactory();
        int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；

        
        Disruptor<LongEvent> disruptor = new Disruptor<>(eventFactory,
                ringBufferSize, new MyThreadFactory(), ProducerType.SINGLE,
                new YieldingWaitStrategy());

        EventHandler<LongEvent> eventHandler = new LongEventHandler();
        disruptor.handleEventsWith(eventHandler);

        disruptor.start();

        return disruptor;
    }

    public static void publishEvent(Disruptor<LongEvent> disruptor, Object data) {
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();//请求下一个事件序号；

        try {
            LongEvent event = ringBuffer.get(sequence);//获取该序号对应的事件对象；
            long d = (long) data;//获取要通过事件传递的业务数据；
            event.setValue(d);
        } finally {
            ringBuffer.publish(sequence);//发布事件；
        }
    }
    
    public static Disruptor getDisruptor(){
        if(disruptor == null){
            synchronized (DisruptorUtil.class){
                if(disruptor == null){
                    disruptor = start();
                }
            }
        }
        
        return disruptor;
    }
}
