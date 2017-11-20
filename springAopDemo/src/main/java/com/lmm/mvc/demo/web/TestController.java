package com.lmm.mvc.demo.web;

import com.lmm.mvc.demo.model.Test;
import com.lmm.mvc.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/12/14.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;
    @ResponseBody
    @RequestMapping("/saveTest")
    public Integer saveTest(){
        Test test = new Test();
        test.setUserName("雷涛"+Thread.currentThread().getName());
        testService.saveTest(test);
        return test.getId();
    }
}
