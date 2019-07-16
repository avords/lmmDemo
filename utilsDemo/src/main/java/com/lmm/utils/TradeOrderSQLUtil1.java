package com.lmm.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author: arno.yan
 * @Date: 2019/3/26
 */
public class TradeOrderSQLUtil1 {
    public static void main(String[] args) throws Exception {

        String sql = "CREATE INDEX IX_BUYER_ID_DOMAIN ON XIMA_TRD_{dbIndex}.TRD_DRAFT_ORDER_{tbIndex} (`BUYER_ID`,`DOMAIN`);\n" +
                "CREATE INDEX IX_BUYER_ID_DOMAIN ON XIMA_TRD_{dbIndex}.TRD_PRICED_ORDER_{tbIndex} (`BUYER_ID`,`DOMAIN`);\n" +
                "CREATE INDEX IX_PRICED_ORDER_ID_ITEM_ID ON XIMA_TRD_{dbIndex}.TRD_PRICED_ORDER_LINE_{tbIndex} (`PRICED_ORDER_ID`,`ITEM_ID`);";
        
        sql = "ALTER TABLE XIMA_TRD_{dbIndex}.TRD_DRAFT_ORDER_{tbIndex} DROP INDEX IX_BUYER_ID;\n" +
                "ALTER TABLE XIMA_TRD_{dbIndex}.TRD_PRICED_ORDER_{tbIndex} DROP INDEX IX_BUYER_ID;\n" +
                "ALTER TABLE XIMA_TRD_{dbIndex}.TRD_PRICED_ORDER_LINE_{tbIndex} DROP INDEX IX_PRICED_ORDER_ID;";
        
        sql = "ALTER TABLE XIMA_TRD_{dbIndex}.TRD_DRAFT_ORDER_{tbIndex} ADD INDEX `IX_LAST_UPDATE_TIME` (`LAST_UPDATE_TIME`);";
        //sql = "ALTER TABLE `XIMA_TRD_{dbIndex}`.`TRD_DRAFT_ORDER_{tbIndex}` CHANGE COLUMN FULFILL_TYPE_ID TRADE_TYPE SMALLINT  DEFAULT NULL;";
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
