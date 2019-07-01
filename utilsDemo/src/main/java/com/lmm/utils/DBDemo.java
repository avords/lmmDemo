package com.lmm.utils;

/**
 * @author: arno.yan
 * @Date: 2019/6/14
 */
public class DBDemo {
    public static void main(String[] args) {
        Long userId = 203715L;

        System.out.println("DB:" + userId % 10 + ",TB:" + userId / 10 % 1000);
    }
}
