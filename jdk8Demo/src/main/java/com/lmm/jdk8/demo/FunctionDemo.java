package com.lmm.jdk8.demo;

import java.util.function.Function;

public class FunctionDemo {
    public static void main(String[] args) {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        backToString.apply("123");     // "123"
        
        Function<Long,Long> f1 = aLong -> aLong*aLong;
        Function<Long,Long> f2 = aLong -> 10*aLong;
        System.out.println(f1.andThen(f2).apply(3L));

        System.out.println(f1.compose(f2).apply(2L));
    }
}
