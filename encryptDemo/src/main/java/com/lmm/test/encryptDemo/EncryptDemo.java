package com.lmm.test.encryptDemo;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Administrator on 2016/11/13.
 */
public class EncryptDemo {
    public static void main(String[] args) throws Exception {
        System.out.println("4的md5:"+DigestUtils.md5Hex("4"));
        Base64 base64 = new Base64();
        System.out.println("4的base64:"+base64.encodeToString("4".getBytes("UTF-8")));
    }
}
