package com.lmm.test.fastJsonDemo;

/**
 * Created by Administrator on 2017/3/7.
 */
public class StaticClassDemo {
    public static void main(String[] args) {
        StaticClass s = null;
        System.out.println(s.a);
        //System.out.println(s.getField1());
        
        s = new StaticClass();
        System.out.println(s.getClass().getSimpleName());
    }
}
