package com.lmm.jdk8.demo;

import java.util.Optional;

public class OptionalDemo2 {

    public static void main(String[] args) {
        User user = new User("zhangxiaolin");
        char c = Optional.ofNullable(user)
                .map(u->u.toString())
                .map(s -> s.toUpperCase())
                .map(s -> s.charAt(0))
                .orElse('1');

        System.out.println(c);
    }
}
