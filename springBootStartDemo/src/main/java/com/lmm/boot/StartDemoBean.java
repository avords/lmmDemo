package com.lmm.boot;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Administrator on 2017/11/23.
 */
public class StartDemoBean {
    @Value("${start.demo.school:北方民资大学}")
    private String school;
    private int age;
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
