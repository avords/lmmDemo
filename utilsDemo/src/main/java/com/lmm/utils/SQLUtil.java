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

        String sql = "ALTER TABLE `XIMA_CRD_07`.`CRD_DRAFT_ORDER_%s` MODIFY COLUMN `CLIENT_AGENT` VARCHAR(256) DEFAULT NULL;\n" +
                "ALTER TABLE `XIMA_CRD_07`.`CRD_DRAFT_ORDER_%s` MODIFY COLUMN `CLIENT_IP`  VARCHAR(256) DEFAULT NULL;\n" +
                "ALTER TABLE `XIMA_CRD_07`.`CRD_DRAFT_ORDER_%s` MODIFY COLUMN `CLIENT_DEVICE_ID`  VARCHAR(256) DEFAULT NULL;\n";
        int tableNum = 100;

        String path = SQLUtil.class.getResource("/").getPath();
        BufferedWriter out = new BufferedWriter(new FileWriter(path + "buildSQL.sql"));

        int bit = String.valueOf(tableNum - 1).length();

        for (int i = 0; i < tableNum; i++) {
            String sqlTemp = String.format(sql, StringUtils.leftPad(String.valueOf(i), bit, "0"),StringUtils.leftPad(String.valueOf(i), bit, "0"),StringUtils.leftPad(String.valueOf(i), bit, "0"));
            out.write(sqlTemp + "\n");
        }

        out.flush();
        out.close();
    }
}
