package com.lmm.jdk8.demo;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author: arno.yan
 * @Date: 2019/3/8
 */
public class StreamTest {
    public static void main(String[] args) {
        String str = Arrays.asList("tom","alice","zhangwei","tom","alice").stream()
                .filter(s->{
                    System.out.println("filter 操作"+s);return s.length()<20;})
                .map(s->{
                    System.out.println("map 操作"+s);return s.toUpperCase();}).sorted().collect(Collectors.joining(","));

        System.out.println(str);
    }
}
