package com.lmm.test.demo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericDemo2<T> {

    protected GenericDemo2() {
    }

    public void test(){
        Type superClass = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];

        System.out.println(type);
    }
}
