package com.kl.quartz;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * @author: arno.yan
 * @Date: 2020/4/8
 */
public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
        Base64 base64 = new Base64(true);
        byte[] bytes = base64.decode("ESeh2iGA5n0mc7h9kkah5aTUobz%2BkCIlwEkqLvExVrzR1InxdPp%2FW6OkUdqjltj%2F");
        System.out.println(new String(bytes,"utf-8"));
    }
}
