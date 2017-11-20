package com.lmm.springdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/9/28.
 */
@Service("taskService")
public class TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    
    public void print(){
        LOGGER.info("我开始执行");
        /*try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
