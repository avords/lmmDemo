package com.lmm.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/2/8.
 */
@Controller
public class HelloController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        String error = request.getParameter("error");
        String logout = request.getParameter("logout");
        model.addAttribute("error",error);
        model.addAttribute("logout",logout);
        return "login";
    }
}
