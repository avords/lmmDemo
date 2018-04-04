package com.lmm.test.demo;

/**
 * Created by Administrator on 2018/3/22.
 */
public class ArrayTree {

    public static void preBianli(int[] a, int index) {
        if (2 * index + 1 < a.length) {
            preBianli(a, 2 * index + 1);
        }
        if (index < a.length) {
            System.out.print(a[index]+",");
        }
        if (2 * index + 2 < a.length) {
            preBianli(a, 2 * index + 2);
        }
    }

    public static void main(String[] args) {
        int[] a = {12,2,3,69,56,21,52,48,96,78,32};
        preBianli(a,0);
    }
}
