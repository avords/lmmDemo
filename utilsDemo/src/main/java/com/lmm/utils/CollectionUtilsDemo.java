package com.lmm.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: arno.yan
 * @Date: 2019/3/12
 */
public class CollectionUtilsDemo {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);

        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(3);
        list2.add(4);
        list2.add(5);
        list2.add(6);

        // 取交集[3, 4]
        Collection<Integer> interColl = CollectionUtils.intersection(list1, list2);
        System.out.println(interColl);// 打印出[3, 4]

        // 取并集[1, 2, 3, 4, 5, 6]
        Collection<Integer> unionColl = CollectionUtils.union(list1, list2);
        System.out.println(unionColl);// 打印出[1, 2, 3, 4, 5, 6]

        // 取差集[1,2]
        Collection<Integer> disColl = CollectionUtils.disjunction(list1, list2);
        System.out.println(disColl);// 打印出[1, 2, 5, 6]

        Collection<Integer> c = CollectionUtils.subtract(list1, list2);
        System.out.println(c);// 打印出[1, 2]
    }
}
