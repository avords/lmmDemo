package com.lmm.disruptorDemo;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmm.disruptorDemo.utils.DisruptorUtil;

/**
 * Created by arno.yan on 2018/11/27.
 */
public class disruptorTest {

    public static void main(String[] args) {
        Disruptor disruptor = DisruptorUtil.getDisruptor();

        //发布事件；
        for(long i=0;i<200;i++){
            DisruptorUtil.publishEvent(disruptor, i);
        }
    }
}
