package com.lmm.distributed.lock.zookeeper;

import java.util.Date;

/**
 * Created by arno.yan on 2019/1/16.
 */
public class StringDemo {

    public static void main(String[] args) {
        Date date = new Date(1545580833000L);
        System.out.println(date);

        Date date1 = new Date(1545667233000L);
        System.out.println(date1);
    }
}
