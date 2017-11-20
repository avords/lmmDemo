package com.lmm.test.demo;

import java.io.InputStream;

/**
 * Created by Administrator on 2017/9/8.
 */
public class MyClassLoader extends ClassLoader{

    public MyClassLoader() {
        super();
    }
    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }
    
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try{
            String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
            InputStream is = getClass().getResourceAsStream(fileName);
            if(is==null){
                return super.loadClass(name);
            }
            byte[] b = new byte[is.available()];
            is.read(b);
            return defineClass(name,b,0,b.length);
        }catch (Exception e){
            throw new ClassNotFoundException(name);
        }
    }
}
