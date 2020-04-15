package com.lmm.test.io;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: arno.yan
 * @Date: 2020/4/15
 */
public class ByteBufferDemo {

    public static void main(String[] args) throws Exception {
        //user.setAge(26);
        RandomAccessFile raf = new RandomAccessFile("/Users/xmly/test.txt", "rw");
        FileChannel fileChannel = raf.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) raf.length());
        fileChannel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));
    }
}
