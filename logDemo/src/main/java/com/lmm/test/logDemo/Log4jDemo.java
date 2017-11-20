package com.lmm.test.logDemo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Administrator on 2016/11/3.
 */
public class Log4jDemo {
    private static final Log LOG = LogFactory.getLog(Log4jDemo.class);
    public static void main(String[] args) {
        LOG.info("sssssdd");
    }
}
