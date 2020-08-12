package com.lmm.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author: arno.yan
 * @Date: 2019/3/26
 */
public class FulfillOrderSQLUtil {
    public static void main(String[] args) throws Exception {

        String sql = "CREATE INDEX IX_BUYER_ID_DOMAIN ON XIMA_TRD_{dbIndex}.TRD_DRAFT_ORDER_{tbIndex} (`BUYER_ID`,`DOMAIN`);\n" +
                "CREATE INDEX IX_BUYER_ID_DOMAIN ON XIMA_TRD_{dbIndex}.TRD_PRICED_ORDER_{tbIndex} (`BUYER_ID`,`DOMAIN`);\n" +
                "CREATE INDEX IX_PRICED_ORDER_ID_ITEM_ID ON XIMA_TRD_{dbIndex}.TRD_PRICED_ORDER_LINE_{tbIndex} (`PRICED_ORDER_ID`,`ITEM_ID`);";

        sql = "ALTER TABLE XIMA_TRD_{dbIndex}.TRD_DRAFT_ORDER_{tbIndex} DROP INDEX IX_BUYER_ID;\n" +
                "ALTER TABLE XIMA_TRD_{dbIndex}.TRD_PRICED_ORDER_{tbIndex} DROP INDEX IX_BUYER_ID;\n" +
                "ALTER TABLE XIMA_TRD_{dbIndex}.TRD_PRICED_ORDER_LINE_{tbIndex} DROP INDEX IX_PRICED_ORDER_ID;";

        //sql = "CREATE TABLE `XIMA_FRD_{dbIndex}`.`FRD_DELIVERY_ORDER_EXPRESS_{tbIndex}` LIKE `XIMA_FRD_{dbIndex}`.`FRD_DELIVERY_ORDER_EXPRESS_00`;";
        sql = "ALTER TABLE `XIMA_FRD_{dbIndex}`.`FRD_DELIVERY_ORDER_LINE_{tbIndex}` change column SKU_ID SPU_ID VARCHAR(20) DEFAULT NULL;\n" +
                "ALTER TABLE `XIMA_FRD_{dbIndex}`.`FRD_PRICED_ORDER_LINE_{tbIndex}` change column SKU_ID SPU_ID VARCHAR(20) DEFAULT NULL;";
        
        sql = "ALTER TABLE `XIMA_FRD_{dbIndex}`.`FRD_PRICED_ORDER_{tbIndex}` ADD COLUMN `RECEIVER_ID` bigint(20) DEFAULT NULL, ADD COLUMN `RECEIVER_DOMAIN` TINYINT DEFAULT NULL AFTER BUYER_DOMAIN;\n"
                +"ALTER TABLE `XIMA_FRD_{dbIndex}`.`FRD_PRICED_ORDER_{tbIndex}` modify COLUMN `BUYER_ID` bigint(20) NOT NULL;\n"
                +"ALTER TABLE `XIMA_FRD_{dbIndex}`.`FRD_DELIVERY_ORDER_{tbIndex}` modify COLUMN `RECEIVER_ID` bigint(20) NOT NULL;\n";
        
        sql = "ALTER TABLE `XIMA_FRD_{dbIndex}`.`FRD_PRICED_ORDER_{tbIndex}` change column PROMOTION_TYPES `TRADE_TYPE` TINYINT(2) DEFAULT NULL;\n";

        int tableNum = 100;
        int dbNum = 10;

        String path = SQLUtil.class.getResource("/").getPath();
        BufferedWriter out82 = new BufferedWriter(new FileWriter(path + "mysql-xima-082.ximalaya.local.sql"));
        BufferedWriter out84 = new BufferedWriter(new FileWriter(path + "mysql-xima-084.ximalaya.local.sql"));

        int tbBit = String.valueOf(tableNum - 1).length();

        for (int i = 0; i < dbNum; i++) {
            for (int j = 0; j < tableNum; j++) {

                String sqlTemp = sql.replaceAll("\\{dbIndex}", StringUtils.leftPad(String.valueOf(i), 2, "0"))
                        .replaceAll("\\{tbIndex}", StringUtils.leftPad(String.valueOf(j), tbBit, "0"));
                if (i % 2 == 0) {
                    out82.write(sqlTemp + "\n");
                } else {
                    out82.write(sqlTemp + "\n");
                }
            }
        }

        out82.flush();
        out82.close();

        out84.flush();
        out84.close();
    }
}
