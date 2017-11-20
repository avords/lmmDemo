package com.lmm.bintree;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/16.
 */
public class SerializeDemo {
    public static void main(String[] args) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        SonClass sonClass = new SonClass();
        map.put("sonClass",sonClass);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(map);
        out.flush();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream in = new ObjectInputStream(inputStream);
        Map map2 = (Map) in.readObject();
    }
}
