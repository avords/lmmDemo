package com.lmm.jdk8.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by arno.yan on 2018/11/6.
 */
public class Test {


    private static String addr = "442001,石岐区\n" +
            "442002,东区\n" +
            "442003,西区\n" +
            "442004,南区\n" +
            "442005,五桂山区\n" +
            "442006,火炬开发区\n" +
            "442007,黄圃镇\n" +
            "442008,南头镇\n" +
            "442009,东凤镇\n" +
            "442010,阜沙镇\n" +
            "442011,小榄镇\n" +
            "442012,东升镇\n" +
            "442013,古镇镇\n" +
            "442014,横栏镇\n" +
            "442015,三角镇\n" +
            "442016,民众镇\n" +
            "442017,南朗镇\n" +
            "442018,港口镇\n" +
            "442019,大涌镇\n" +
            "442020,沙溪镇\n" +
            "442021,三乡镇\n" +
            "442022,板芙镇\n" +
            "442023,神湾镇\n" +
            "442024,坦洲镇";

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            System.out.println(random.nextInt(10000000));
        }
    }
}
