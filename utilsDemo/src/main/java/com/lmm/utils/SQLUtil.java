package com.lmm.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author: arno.yan
 * @Date: 2019/3/26
 */
public class SQLUtil {
    public static void main(String[] args) throws Exception {

        String sql = "CREATE TABLE `XIMA_URD_0`.`URD_USER_ORDER_%s` (\n" +
                "  `USER_ORDER_ID` bigint(20) NOT NULL,\n" +
                "  `BUYER_ID` bigint(20) NOT NULL,\n" +
                "  `DOMAIN` TINYINT DEFAULT '1',\n" +
                "  `BUSINESS_TYPE_ID` SMALLINT NOT NULL,\n" +
                "  `TRADE_ORDER_NO` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,\n" +
                "  `RELATIVE_ORDER_NO` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,\n" +
                "  `ORDER_FROM` TINYINT NOT NULL,\n" +
                "  `STATUS_ID` TINYINT NOT NULL,\n" +
                "  `CREATE_TIME` datetime not null,\n" +
                "  PRIMARY KEY (`USER_ORDER_ID`),\n" +
                "  KEY `IX_BUYER_ID_DOMAIN_BUSINESS_TYPE_ID_CREATE_TIME` (`BUYER_ID`,`DOMAIN`,`BUSINESS_TYPE_ID`,`CREATE_TIME`),\n" +
                "  KEY `IX_BUYER_ID_DOMAIN_STATUS_ID_CREATE_TIME` (`BUYER_ID`,`DOMAIN`,`STATUS_ID`,`CREATE_TIME`),\n" +
                "  KEY `IX_BUYER_ID_CREATE_TIME` (`BUYER_ID`,`CREATE_TIME`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";
        int tableNum = 1000;

        String path = SQLUtil.class.getResource("/").getPath();
        BufferedWriter out = new BufferedWriter(new FileWriter(path + "buildSQL.sql"));

        int bit = String.valueOf(tableNum - 1).length();

        for (int i = 0; i < tableNum; i++) {
            String sqlTemp = String.format(sql, StringUtils.leftPad(String.valueOf(i), bit, "0"));
            out.write(sqlTemp + "\n");
        }

        out.flush();
        out.close();
    }
}
