package com.lmm.jdk8.demo;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by arno.yan on 2018/11/6.
 */
public class Test {

    public static void main(String[] args) {
        String sql = "CREATE UNIQUE INDEX UNIQUE_STATUS_ID ON XIMA_IVC.IVC_INVOICEABLE_ORDER_%s (STATUS_ID);";

        for (int i = 0; i < 100; i++) {
            System.out.println(String.format(sql, StringUtils.leftPad(String.valueOf(i), 2, '0')));
        }
    }
}
