package com.lmm.jdk8.demo;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SupplierDemo {
    public static void main(String[] args) {
        Supplier<ArrayList> personSupplier = ArrayList::new;
        System.out.println(personSupplier.get());  // new Person
        
        Supplier<Integer> s1 = () -> Integer.valueOf(2);
        System.out.println(s1.get());
    }
}
