package com.lmm.test.demo;

public class GenericDemo1<T> {
    
    public void test(){
        new GenericDemo2<T>(){}.test();
    }
}
