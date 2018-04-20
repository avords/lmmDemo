package com.lmm.jdk8.demo;

import java.util.function.Consumer;

public class consumerDemo {
    public static void main(String[] args) {
        Consumer<Integer> greeter = (p) -> System.out.println("Hello, " + p);
        greeter.andThen(str-> System.out.println("haha"+str)).accept(12);
    }
}
