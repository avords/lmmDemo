package com.lmm.test.serialize;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by arno.yan on 2018/9/19.
 */
public class Demo {

    public static void main(String[] args) throws IOException {
        User user = new User("闫冬全","风中");
        //user.setAge(26);
        FileOutputStream fos = new FileOutputStream("test.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(user);

        fos.close();
    }
}
