package com.lmm.test.zkDemo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/27.
 */
public class User implements Serializable{
    private int age;
    private String school;
    private String name;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
    
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
