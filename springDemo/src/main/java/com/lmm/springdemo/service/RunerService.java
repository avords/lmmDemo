package com.lmm.springdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2017/9/28.
 */
@Service
public class RunerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RunerService.class);
    @Autowired
    private ExecutorService executorService;
    
    /*@PostConstruct
    public void init(){
        for(int i=0;i<100;i++){
            executorService.execute(new Runer(i));
            LOGGER.info(Thread.currentThread().getName()+"下标："+i);
        }
    }*/
}
