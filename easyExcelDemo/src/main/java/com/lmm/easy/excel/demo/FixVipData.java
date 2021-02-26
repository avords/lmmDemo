package com.lmm.easy.excel.demo;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author: arno.yan
 * @Date: 2020/10/23
 */
public class FixVipData {

    public static void main(String[] args) throws Exception {

        String path = PathUtils.getRootPath();

        BufferedWriter out82 = new BufferedWriter(new FileWriter(path + "mysql-xima-159.ximalaya.local.sql"));
        BufferedWriter out84 = new BufferedWriter(new FileWriter(path + "mysql-xima-162.ximalaya.local.sql"));

        BufferedReader read = new BufferedReader(new FileReader(path + "fix.csv"));


        String line = read.readLine();
        while (StringUtils.isNotEmpty(line)) {
            String[] fileds = line.split(",");
            Long userId = Long.valueOf(fileds[1]);
            Integer domain = Integer.valueOf(fileds[2]);
            Long categoryId = Long.valueOf(fileds[3]);
            String tradeOrderNo = "'" + fileds[4] + "'";
            String spuFulfillOrderNo = tradeOrderNo;
            Long itemId = StringUtils.isBlank(fileds[6]) ? 0L : Long.valueOf(fileds[6]);
            Long quantity = 1L;
            String spuId = "'100000'";
            Long durationSeconds = Long.valueOf(fileds[14]) * 86400L;
            String startTime = "'" + fileds[15] + "'";
            String endTime = "'" + fileds[16] + "'";
            Boolean isValid = Boolean.valueOf(fileds[17]);
            String refundTime = StringUtils.isBlank(fileds[19]) ? null : "'" + fileds[19] + "'";
            String createTime = "'" + fileds[20] + "'";
            String lastUpdateTime = "'" + fileds[21] + "'";
            Long version = Long.valueOf(fileds[22]);

            long dbIndex = userId % 10;
            long tbIndex = userId / 10 % 1000;

            String sql = "INSERT INTO XIMA_FRD_RCD_" + StringUtils.leftPad(String.valueOf(dbIndex), 2, "0") + ".FRD_RCD_DELIVERY_RECORD_" + StringUtils.leftPad(String.valueOf(tbIndex), 3, "0") +
                    "(" +
                    "USER_ID," +
                    "DOMAIN," +
                    "CATEGORY_ID," +
                    "TRADE_ORDER_NO," +
                    "FULFILL_ORDER_NO," +
                    "SPU_FULFILL_ORDER_NO," +
                    "ITEM_ID," +
                    "QUANTITY," +
                    "SPU_ID," +
                    "DURATION_SECONDS," +
                    "START_TIME," +
                    "END_TIME," +
                    "IS_VALID," +
                    "REFUND_TIME," +
                    "CREATE_TIME," +
                    "LAST_UPDATE_TIME," +
                    "VERSION" +
                    ")" +
                    "VALUES ";

            sql = sql + "(" + userId + "," +
                    domain + "," +
                    categoryId + "," +
                    tradeOrderNo + ",null," +
                    spuFulfillOrderNo + "," +
                    itemId + "," +
                    quantity + "," +
                    spuId + "," +
                    durationSeconds + "," +
                    startTime + "," +
                    endTime + "," +
                    isValid + "," +
                    refundTime + "," +
                    createTime + "," +
                    lastUpdateTime + "," +
                    version + ");" + "\n";

            if (dbIndex < 5) {
                out82.write(sql);
            } else {
                out84.write(sql);
            }
            line = read.readLine();
        }
        
        out82.flush();
        out84.flush();
    }
}
