package com.lmm.socket.UDP.clent;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by Administrator on 2017/6/14.
 */
public class UDPClient {
    public static void main(String[] args) throws Exception {
        String text = "你好！世界";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.clear();
        buf.put(text.getBytes());
        buf.flip();
        DatagramChannel channel = DatagramChannel.open();
        channel.send(buf, new InetSocketAddress("127.0.0.1", 8080));
    }
}
