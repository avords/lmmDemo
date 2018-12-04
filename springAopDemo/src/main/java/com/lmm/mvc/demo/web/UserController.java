package com.lmm.mvc.demo.web;

import com.lmm.mvc.demo.aposervice.AopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/11/15.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private AopService aopService;

    @RequestMapping("/getName")
    @ResponseBody
    public String getName(HttpServletRequest request, HttpServletResponse response){
        return aopService.getName();
    }

    @RequestMapping("/getAge")
    @ResponseBody
    public int getAge(HttpServletRequest request, HttpServletResponse response){
        return aopService.getAge();
        
    }

    @RequestMapping("/getSchool")
    @ResponseBody
    public String getSchool(HttpServletRequest request, HttpServletResponse response){
        return aopService.getSchool();

    }
}
