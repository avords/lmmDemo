package com.lmm.test.beanUtilsDemo.vo1;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/9.
 */
public class User implements Serializable{
    private String name;
    private int age;
    
    private Schoole schoole;

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

    public Schoole getSchoole() {
        return schoole;
    }

    public void setSchoole(Schoole schoole) {
        this.schoole = schoole;
    }
}
