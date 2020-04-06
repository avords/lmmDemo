package com.kl.util;

import java.util.Random;

/**
 * @author: arno.yan
 * @Date: 2020/4/6
 */
public class IPUtils {

    public static String generateIP() {

        Random random = new Random();
        return "118" + "." + random.nextInt(255)
                + "." + random.nextInt(255)
                + "." + random.nextInt(255);
    }
}
