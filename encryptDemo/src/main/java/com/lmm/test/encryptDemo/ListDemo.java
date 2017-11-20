package com.lmm.test.encryptDemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
public class ListDemo {
    public static void main(String[] args) {
        List list = Collections.singletonList(12L);
        Long id = (Long) list.remove(0);
        
        System.out.println(id);
    }
}
