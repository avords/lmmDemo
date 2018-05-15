package com.lmm.test.demo;

public class TestGeneric {
    
    public static void main(String[] args) {
        /*GenericDemo2<User> genericDemo2 = new GenericDemo2<User>(){};
        Type superClass = genericDemo2.getClass().getGenericSuperclass();

        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];

        System.out.println(type);*/
        
        GenericDemo1<User> genericDemo1 = new GenericDemo1<User>();
        genericDemo1.test();
    }
}
