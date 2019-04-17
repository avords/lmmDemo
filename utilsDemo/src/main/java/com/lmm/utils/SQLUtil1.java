package com.lmm.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author: arno.yan
 * @Date: 2019/3/26
 */
public class SQLUtil1 {
    public static void main(String[] args) throws Exception {

        String[] sqls = {"CREATE TABLE `XIMA_CRD_00`.`CRD_DRAFT_ORDER_%s` LIKE `XIMA_CRD_00`.`CRD_DRAFT_ORDER_00`;",
        "CREATE TABLE `XIMA_CRD_00`.`CRD_DRAFT_ORDER_ITEM_%s` LIKE `XIMA_CRD_00`.`CRD_DRAFT_ORDER_ITEM_00`;",
        "CREATE TABLE `XIMA_CRD_00`.`CRD_PRICED_ORDER_%s` LIKE `XIMA_CRD_00`.`CRD_PRICED_ORDER_00`;",
        "CREATE TABLE `XIMA_CRD_00`.`CRD_PRICED_ORDER_LINE_%s` LIKE `XIMA_CRD_00`.`CRD_PRICED_ORDER_LINE_00`;",
        "CREATE TABLE `XIMA_CRD_00`.`CRD_ORDER_STATUS_RECORD_%s` LIKE `XIMA_CRD_00`.`CRD_ORDER_STATUS_RECORD_00`;"};
        int tableNum = 100;

        String path = SQLUtil1.class.getResource("/").getPath();
        BufferedWriter out = new BufferedWriter(new FileWriter(path + "buildSQL.sql"));

        int bit = String.valueOf(tableNum - 1).length();

        for (String sql : sqls) {
            for (int i = 1; i < tableNum; i++) {
                String sqlTemp = String.format(sql, StringUtils.leftPad(String.valueOf(i), bit, "0"));
                out.write(sqlTemp + "\n");
            }
        }

        out.flush();
        out.close();
    }
}
