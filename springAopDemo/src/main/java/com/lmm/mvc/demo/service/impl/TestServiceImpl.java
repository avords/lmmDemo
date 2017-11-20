package com.lmm.mvc.demo.service.impl;

import com.lmm.mvc.demo.dao.TestDao;
import com.lmm.mvc.demo.model.Test;
import com.lmm.mvc.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/12/14.
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDao;
    @Override
    public void saveTest(Test test) {
        testDao.saveTest(test);
    }
}
