package com.lmm.test.demo;

import sun.reflect.Reflection;

/**
 * Created by Administrator on 2017/9/8.
 */
public class User {
    private String name;
    private String school;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    public Class getCurrentUserClass(){
        return Reflection.getCallerClass(0);
    }
}
