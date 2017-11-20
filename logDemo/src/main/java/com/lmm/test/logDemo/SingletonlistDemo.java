package com.lmm.test.logDemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public class SingletonlistDemo {
    public static void main(String[] args) {
        List list = new ArrayList<>();
        list.add(123L);
        list.add(456L);
        list = Collections.singletonList(list);
        System.out.println(list);
    }
}
