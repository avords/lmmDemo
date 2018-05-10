package com.lmm.jdk8.demo;

public class User {
    private String userName;
    public User(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return this.userName;
    }
}
