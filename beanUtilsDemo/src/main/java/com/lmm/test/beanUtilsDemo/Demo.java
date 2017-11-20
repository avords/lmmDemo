package com.lmm.test.beanUtilsDemo;

import com.lmm.test.beanUtilsDemo.vo1.Schoole;

/**
 * Created by Administrator on 2016/11/9.
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        com.lmm.test.beanUtilsDemo.vo1.User user1 = new com.lmm.test.beanUtilsDemo.vo1.User();
        Schoole schoole1 = new Schoole();
        user1.setName("sss");
        user1.setAge(21);
        schoole1.setName("中华小学");
        schoole1.setDizhi("浦东");
        user1.setSchoole(schoole1);
        com.lmm.test.beanUtilsDemo.vo2.User user2 = new com.lmm.test.beanUtilsDemo.vo2.User();
        //apche
        //BeanUtils.copyProperties(user2,user1);
        org.springframework.beans.BeanUtils.copyProperties(user1,user2,new String[]{"schoole"});
        System.out.println(user2.getName());
        //两者都不能复制类型不一样但是名字一样的对象，比如schoole
    }
}
