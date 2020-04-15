package com.lmm.test.serialize;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by arno.yan on 2018/9/19.
 */
public class Demo {

    public static void main(String[] args) throws IOException {
        //user.setAge(26);
        File file = new File("/Users/xmly/test.txt");
        FileInputStream fos = new FileInputStream(file);
        MappedByteBuffer mappedByteBuffer =
                fos.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());

        byte[] s = new byte[mappedByteBuffer.capacity()];
        mappedByteBuffer.get(s);
        /*while (mappedByteBuffer.hasRemaining()) {
            byte b = mappedByteBuffer.get();
            s[mappedByteBuffer.position()-1] = b;
        }*/
        System.out.println(new String(s));
        fos.close();
    }
}
