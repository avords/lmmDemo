package com.lmm.test.fastJsonDemo.vo2;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class User {
    private String name;
    private int age;
    private Date birthday;
    private int staticF;

    private Schoole schoole;
    private List<Schoole> schooles;

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

    public List<Schoole> getSchooles() {
        return schooles;
    }

    public void setSchooles(List<Schoole> schooles) {
        this.schooles = schooles;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public  int getStaticF() {
        return staticF;
    }

    public void setStaticF(int staticF) {
        this.staticF = staticF;
    }
}