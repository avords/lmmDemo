package com.lmm.test.demo;

import sun.reflect.Reflection;

import java.util.Objects;

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

    public User() {
    }

    public User(String name, String school, int age) {
        this.name = name;
        this.school = school;
        this.age = age;
    }

    public Class getCurrentUserClass(){
        return Reflection.getCallerClass(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age;
    }

    @Override
    public int hashCode() {

        return Objects.hash(age);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", school='" + school + '\'' +
                ", age=" + age +
                '}';
    }
}
