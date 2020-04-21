package com.lmm.jdk8.demo;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by arno.yan on 2018/9/19.
 */
public class MappedByteBufferDemo {

    public static void main(String[] args) throws IOException {
        //user.setAge(26);
        RandomAccessFile raf = new RandomAccessFile("/Users/xmly/test.txt", "rw");
        FileChannel fileChannel = raf.getChannel();
        
        MappedByteBuffer mappedByteBuffer =
                fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, raf.length());

        byte[] s = new byte[mappedByteBuffer.capacity()];
        mappedByteBuffer.get(s);
        /*while (mappedByteBuffer.hasRemaining()) {
            byte b = mappedByteBuffer.get();
            s[mappedByteBuffer.position()-1] = b;
        }*/
        System.out.println(new String(s));
        
        mappedByteBuffer.flip();
        mappedByteBuffer.put("你是".getBytes());
        raf.close();
        fileChannel.close();
    }
}
