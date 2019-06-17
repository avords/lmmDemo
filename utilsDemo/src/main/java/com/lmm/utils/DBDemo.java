package com.lmm.utils;

/**
 * @author: arno.yan
 * @Date: 2019/6/14
 */
public class DBDemo {
    public static void main(String[] args) {
        Long userId = 44880142L;

        System.out.println("DB:" + userId % 8 + ",TB:" + userId /8);
    }
}
