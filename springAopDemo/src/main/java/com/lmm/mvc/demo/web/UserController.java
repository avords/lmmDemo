package com.lmm.mvc.demo.web;

import com.lmm.mvc.demo.model.User;
import com.lmm.mvc.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    private UserService userService;
    
    @RequestMapping("/getUserById/{id}")
    @ResponseBody
    public User getUserById(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id){
        User user = userService.getUserById(123L);
        return user;
    }
}
