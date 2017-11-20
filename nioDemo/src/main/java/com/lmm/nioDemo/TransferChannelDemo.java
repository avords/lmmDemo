package com.lmm.nioDemo;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2017/6/14.
 */
public class TransferChannelDemo {
    public static void main(String[] args) throws Exception {
        RandomAccessFile fromFile = new RandomAccessFile("d:/nio/fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("d:/nio/toFile.txt", "rw");
        FileChannel      toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        toChannel.transferFrom(fromChannel,position, count);
    }
}
