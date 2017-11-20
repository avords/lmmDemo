package com.lmm.test.beanUtilsDemo;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public class Demo1 {
    public static void main(String[] args) {
        List<com.lmm.test.beanUtilsDemo.vo1.User> list1 = new ArrayList<>();
        com.lmm.test.beanUtilsDemo.vo1.User user1 = new com.lmm.test.beanUtilsDemo.vo1.User();
        user1.setName("sss");
        user1.setAge(21);
        list1.add(user1);
        List<com.lmm.test.beanUtilsDemo.vo2.User> list2 = new ArrayList<>();
        BeanUtils.copyProperties(list1,list2);
        System.out.println(list2);
    }
}
