package com.lmm.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: arno.yan
 * @Date: 2019/9/11
 */
public class Test {
    public static void main(String[] args) {
        List<Long> list1 = new ArrayList<>(10000000);
        LinkedList<Long> list2 = new LinkedList<>();

        long time3 = System.currentTimeMillis();
        for (long i = 0; i < 10000000; i++) {
            list2.addFirst(i);
        }
        long time4 = System.currentTimeMillis();
        System.out.println("LinkedList:"+(time4-time3));
        
        long time1 = System.currentTimeMillis();
        for (long i = 0; i < 10000000; i++) {
            list1.add(0,i);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("ArrayList:"+(time2-time1));
    }
}
