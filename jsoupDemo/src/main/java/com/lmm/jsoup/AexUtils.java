package com.lmm.jsoup;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/2/9.
 */
public class AexUtils {
    public static Double getDogPrice(){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.aex.com/trade/getTradeList30.php?coinname=DOGE&mk_type=BITCNY")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = doc.body().text();
        if(StringUtils.isNotBlank(json)){
            Number price = (Number) JSON.parseObject(json).getJSONArray("tradeStr").getJSONArray(0).get(1);
            return price.doubleValue();
        }
        return 0D;
    }
    public static Double getBTCPrice(){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.aex.com/trade/getTradeList30.php?coinname=BTC&mk_type=BITCNY")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = doc.body().text();
        if(StringUtils.isNotBlank(json)){
            Number price = (Number) JSON.parseObject(json).getJSONArray("tradeStr").getJSONArray(0).get(1);
            return price.doubleValue();
        }
        return 0D;
    }
}
