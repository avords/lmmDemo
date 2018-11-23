package com.lmm.jdk8.demo;

/**
 * Created by arno.yan on 2018/11/6.
 */
public class Test {

    public static void main(String[] args) {
        User user = new User("李强");

        long starDate = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            if (User.class.isAssignableFrom(user.getClass())) {
                
            }
        }

        System.out.println(System.currentTimeMillis() - starDate);

        starDate = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            if (user instanceof User) {
                
            }
        }
        System.out.println(System.currentTimeMillis() - starDate);
    }
}
