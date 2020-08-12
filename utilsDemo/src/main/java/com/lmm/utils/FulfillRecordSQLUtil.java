package com.lmm.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author: arno.yan
 * @Date: 2019/3/26
 */
public class FulfillRecordSQLUtil {
    public static void main(String[] args) throws Exception {

        String sql = "CREATE INDEX IX_BUYER_ID_DOMAIN ON XIMA_TRD_{dbIndex}.TRD_DRAFT_ORDER_{tbIndex} (`BUYER_ID`,`DOMAIN`);\n" +
                "CREATE INDEX IX_BUYER_ID_DOMAIN ON XIMA_TRD_{dbIndex}.TRD_PRICED_ORDER_{tbIndex} (`BUYER_ID`,`DOMAIN`);\n" +
                "CREATE INDEX IX_PRICED_ORDER_ID_ITEM_ID ON XIMA_TRD_{dbIndex}.TRD_PRICED_ORDER_LINE_{tbIndex} (`PRICED_ORDER_ID`,`ITEM_ID`);";

        sql = "ALTER TABLE XIMA_URD_{dbIndex}.URD_USER_ORDER_{tbIndex} DROP INDEX IX_BUYER_ID_DOMAIN_BUSINESS_TYPE_ID_CREATE_TIME;\n" +
                "ALTER TABLE XIMA_URD_{dbIndex}.URD_USER_ORDER_{tbIndex} DROP INDEX IX_BUYER_ID_DOMAIN_STATUS_ID_CREATE_TIME;";

        sql = "CREATE TABLE `XIMA_FRD_RCD_{dbIndex}`.`FRD_RCD_DELIVERY_RECORD_{tbIndex}` LIKE `XIMA_FRD_RCD_{dbIndex}`.`FRD_RCD_DELIVERY_RECORD_000`;";
        int tableNum = 1000;
        int dbNum = 10;

        String path = SQLUtil.class.getResource("/").getPath();
        BufferedWriter out82 = new BufferedWriter(new FileWriter(path + "record82.ximalaya.local.sql"));
        BufferedWriter out84 = new BufferedWriter(new FileWriter(path + "record84.ximalaya.local.sql"));

        int tbBit = String.valueOf(tableNum - 1).length();

        for (int i = 0; i < dbNum; i++) {
            for (int j = 0; j < tableNum; j++) {

                String sqlTemp = sql.replaceAll("\\{dbIndex}", StringUtils.leftPad(String.valueOf(i), 2, "0"))
                        .replaceAll("\\{tbIndex}", StringUtils.leftPad(String.valueOf(j), tbBit, "0"));
                if (i < 4 || i == 8) {
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
