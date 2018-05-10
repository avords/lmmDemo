package com.lmm.jdk8.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamDemo {

    public static void main(String[] args) {
       /* Arrays.asList("tom","alice","zhangwei").forEach(str-> System.out.printf(str+","));
        Arrays.asList("tom","alice","zhangwei").forEach(System.out::print);
        String str = Arrays.asList("tom","alice","zhangwei").stream().map(String::toUpperCase).collect(joining(";"));
        System.out.printf("\n"+str);*/

        List<User> userList = Arrays.asList("tom","alice","zhangwei").stream().map(st->{
            return new User(st);
        }).collect(Collectors.toList());

        System.out.println(userList);
    }
}
