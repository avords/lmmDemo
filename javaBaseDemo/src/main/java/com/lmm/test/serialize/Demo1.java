package com.lmm.test.serialize;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by arno.yan on 2018/9/19.
 */
public class Demo1 {
    public static void main(String[] args) throws IOException, Exception {
        FileInputStream fis = new FileInputStream("test.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();

        System.out.println(obj);
        ois.close();
    }
}
