package com.lmm.nioDemo;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2017/6/14.
 */
public class WriteFileChannelDemo {
    public static void main(String[] args) throws Exception {
        RandomAccessFile file = new RandomAccessFile("D:/nio/nio-write.txt","rw");
        FileChannel channel = file.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("中华人民共和国，社会主义奔小康，中华人民共和国，社会主义奔小康中华人民共和国".getBytes("utf-8"));
        buffer.flip();
        buffer.put("找时间".getBytes("utf-8"));
        buffer.flip();
        channel.write(buffer);
        channel.close();
    }
}
