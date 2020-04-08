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
        byte[] bytes = base64.decode("gytwVZo5sVX9UDMUEDnZ6iLerk2cNDpY4+EOeLDi9aRIdb3yIJHgP4R97iXKamGTn+KjcFJ4zRJAUAK6aw8MWeHbbOCXjksjJrmcbVKamtZILYAABc46lMjKAXZfeJxt");
        System.out.println(new String(bytes,"gbk"));
    }
}
