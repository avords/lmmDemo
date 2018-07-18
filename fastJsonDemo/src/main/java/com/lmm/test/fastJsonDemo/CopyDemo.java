package com.lmm.test.fastJsonDemo;

import com.alibaba.fastjson.JSON;
import com.lmm.test.fastJsonDemo.vo1.User;

import java.util.Date;

public class CopyDemo {

    public static void main(String[] args) {

        User user = new User();
        user.setAge(11);
        user.setName("1111");
        user.setBirthday(new Date());
        System.out.println(JSON.toJSONString(user));

        User user1 = JSON.parseObject("{\"age\":11,\"birthday\":1531364140751,\"name\":\"1111\",\"myTest\":\"myTest\"}", User.class);
        System.out.println(user1.getAge());
    }
}
