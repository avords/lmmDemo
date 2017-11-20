package com.lmm.test.logDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/11/3.
 */
public class Slf4jDemo {
    private static final Logger logger = LoggerFactory.getLogger(Slf4jDemo.class);

    public static void main(String[] args) {
        logger.info("你是我的小苹果{}也是对的{}赵天亮{}",111,222);
        
    }
}
