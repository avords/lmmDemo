package com.lmm.test.beanUtilsDemo;

import com.lmm.test.beanUtilsDemo.vo1.User;

/**
 * Created by Administrator on 2016/11/30.
 */
public class HessionDemo {
    public static void main(String[] args) {
        User user = new User();
        user.setAge(11);
        user.setName("雷涛");
        byte[] ss = BeanHessionSerializeUtil.serialize(user);
        com.lmm.test.beanUtilsDemo.vo2.User user1 
                = (com.lmm.test.beanUtilsDemo.vo2.User) BeanHessionSerializeUtil.deserialize(ss);
        System.out.println(user1.getName());
    }
}
