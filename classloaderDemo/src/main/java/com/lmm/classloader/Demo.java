package com.lmm.classloader;

import java.net.URL;

/**
 * Created by Administrator on 2016/12/6.
 */
public class Demo {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        URL uri = Thread.currentThread().getContextClassLoader().getResource("");
        String path = uri.getPath();
        MyClassLoader myClassLoader = new MyClassLoader();
        myClassLoader.setPath(path);

        Class clazz = myClassLoader.loadClass("User");
        Object object = clazz.newInstance();
        System.out.println(object);
    }
}
