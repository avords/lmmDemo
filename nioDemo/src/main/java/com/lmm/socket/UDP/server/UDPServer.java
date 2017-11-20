package com.lmm.socket.UDP.server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2017/6/14.
 */
public class UDPServer {
    public static void main(String[] args) throws Exception {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(8080));
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.clear();
        channel.receive(buf);
        buf.flip();
        String str =  Charset.forName("UTF-8").newDecoder().decode(buf).toString();
        System.out.println(str);
    }
}
