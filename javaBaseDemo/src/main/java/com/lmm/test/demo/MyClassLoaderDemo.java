package com.lmm.test.demo;

/**
 * Created by Administrator on 2017/9/8.
 */
public class MyClassLoaderDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        Thread.currentThread().setContextClassLoader(new MyClassLoader());
        Class<User> userClass = (Class<User>) Thread.currentThread().getContextClassLoader().loadClass("User");
        System.out.println(userClass.getClassLoader());
        User user = new User();
        System.out.println(user.getClass().getClassLoader());
        
    }
}
