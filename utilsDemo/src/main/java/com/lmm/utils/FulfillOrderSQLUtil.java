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
        
        sql = "CREATE TABLE `XIMA_FRD_{dbIndex}`.`FRD_FULFILL_SUB_ORDER_{tbIndex}` LIKE `XIMA_FRD_{dbIndex}`.`FRD_FULFILL_SUB_ORDER_00`;";
        //sql = "UPDATE XIMA_TRD_{dbIndex}.TRD_ORDER_STATUS_RECORD_{tbIndex} SET STATUS_ID=2 WHERE STATUS_ID=4;";
        
        int tableNum = 100;
        int dbNum = 10;

        String path = SQLUtil.class.getResource("/").getPath();
        BufferedWriter out82 = new BufferedWriter(new FileWriter(path + "mysql-xima-082.ximalaya.local.sql"));
        BufferedWriter out84 = new BufferedWriter(new FileWriter(path + "mysql-xima-084.ximalaya.local.sql"));

        int tbBit = String.valueOf(tableNum - 1).length();

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < tableNum; j++) {
                
                String sqlTemp = sql.replaceAll("\\{dbIndex}", StringUtils.leftPad(String.valueOf(i), 2, "0"))
                        .replaceAll("\\{tbIndex}", StringUtils.leftPad(String.valueOf(j), tbBit, "0"));
                if(i%2==0) {
                    out82.write(sqlTemp + "\n");
                }else{
                    out84.write(sqlTemp + "\n");
                }
            }
        }

        out82.flush();
        out82.close();

        out84.flush();
        out84.close();
    }
}
