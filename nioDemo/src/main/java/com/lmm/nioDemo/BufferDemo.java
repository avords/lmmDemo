package com.lmm.nioDemo;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2017/6/14.
 */
public class BufferDemo {
    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put("你是我的小呀小苹果".getBytes("GBK"));
        System.out.println("position:"+buffer.position()+",limit:"+buffer.limit()+",capacity:"+buffer.capacity());
        buffer.flip();
        System.out.println("position:"+buffer.position()+",limit:"+buffer.limit()+",capacity:"+buffer.capacity());
        buffer.put("民国".getBytes("GBK"));
        buffer.flip();
        System.out.println("position:"+buffer.position()+",limit:"+buffer.limit()+",capacity:"+buffer.capacity());
        System.out.println(buffer.asCharBuffer().get(1));
    }
}
