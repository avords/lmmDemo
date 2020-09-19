package com.lmm.mvc.controller;

import com.lmm.boot.StartDemoBean;
import com.lmm.boot.StartDemoProperties;
import com.lmm.mvc.service.TestService;
import com.lmm.mvc.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/11/22.
 */
@EnableElasticsearchRepositories(basePackages = "com.lmm")
@SpringBootApplication(scanBasePackages = {"com.lmm"})
@Controller
public class SampleController {
    @Autowired
    private TestService testService;
    @Autowired
    private StartDemoProperties startDemoProperties;
    @Autowired
    private StartDemoBean startDemoBean;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }

    @RequestMapping("/getBean/{name}")
    @ResponseBody
    public boolean getBean(@PathVariable String name) {
        boolean flag = false;
        Object object = SpringUtil.getBean(name);
        if (object != null) {
            flag = true;
        }
        return flag;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        String a = startDemoProperties.getA();
        String b = startDemoProperties.getB();
        long x = 0x7fffffffffffffffL;
        return "a:" + a + ",b:" + b;
    }

    @RequestMapping("/startBean")
    @ResponseBody
    public StartDemoBean getStartDemoBean() {
        startDemoBean.setAge(256);
        return startDemoBean;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }


    @RequestMapping("/execCmd")
    @ResponseBody
    public void execCmd(String cmd, HttpServletResponse response) {
        try {
            int line = 0;
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String content = br.readLine();
            while (content != null && line < 1000) {
                response.getWriter().println(content);
                response.getWriter().flush();
                content = br.readLine();
                line++;
                //content = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
