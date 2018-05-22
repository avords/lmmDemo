package com.lmm.test.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorDemo {

    public static void main(String[] args) {
        
        List<User> list = new ArrayList<>();
        list.add(new User("李红1","北方民族大学",12));
        list.add(new User("李红2","北方民族大学",4));
        list.add(new User("李红3","北方民族大学",7));
        list.add(new User("李红4","北方民族大学",54));

        System.out.println(list);
        Collections.sort(list,(o1, o2) -> o1.getAge()-o2.getAge());
        System.out.println(list);
        
        System.out.println(list);
        Collections.sort(list, Comparator.comparingInt(User::getAge));
        System.out.println("排序后"+list);
    }
}
