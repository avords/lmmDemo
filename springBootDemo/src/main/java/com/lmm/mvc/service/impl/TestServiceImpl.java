package com.lmm.mvc.service.impl;

import com.lmm.mvc.service.TestService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/11/22.
 */
@Service("testService")
public class TestServiceImpl implements TestService {
    @Override
    public void print(String str) {
        System.out.println(str);
    }
}
