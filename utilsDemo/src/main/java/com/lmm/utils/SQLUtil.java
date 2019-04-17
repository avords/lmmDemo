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

        String sql = "CREATE TABLE `XIMA_URD_3`.`URD_USER_ORDER_%s` LIKE `XIMA_URD_3`.`URD_USER_ORDER_000`;";
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
