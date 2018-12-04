package com.lmm.mvc.demo.aposervice;

import org.springframework.stereotype.Service;

/**
 * Created by arno.yan on 2018/11/29.
 */
@Service
public class AopService  extends AbstractService{
    @Override
    public int getAge() {
        return 26;
    }
    
    public String getSchool(){
        return "上海大学";
    }
}
