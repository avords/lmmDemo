package com.lmm.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: arno.yan
 * @Date: 2019/9/11
 */
public class Test1 {
    public static void main(String[] args) {

        List<Integer> list2 = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toCollection(LinkedList::new));
        long time3 = System.currentTimeMillis();
        IntStream.rangeClosed(1, 100000).forEach(i -> list2.add(ThreadLocalRandom.current().nextInt(100000), i));
        long time4 = System.currentTimeMillis();
        System.out.println("LinkedList:" + (time4 - time3));

        List<Integer> list1 = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
        long time1 = System.currentTimeMillis();
        IntStream.rangeClosed(1, 100000).forEach(i -> list1.add(ThreadLocalRandom.current().nextInt(100000), i));
        long time2 = System.currentTimeMillis();
        System.out.println("ArrayList:" + (time2 - time1));
    }
}
