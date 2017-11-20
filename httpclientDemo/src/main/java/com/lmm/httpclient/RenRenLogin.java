package com.lmm.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/8.
 */
public class RenRenLogin {
    public static void main(String[] args) throws Exception {
        WebClient webClient = new WebClient();
        Map map = new HashMap();
        map.put("email","827537797@qq.com");
        map.put("password","cc123456");
        //ajax
        CloseableHttpResponse response = webClient.post("http://www.renren.com/ajaxLogin/login?1=1&uniqueTimestamp=20161141518406",map);
        String respnseStr = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = JSON.parseObject(respnseStr);
        String location = (String) jsonObject.get("homeUrl");
        if(StringUtils.isNotBlank(location)){
            System.out.println("登录成功");
        }else{
            System.out.println("登录失败");
            return;
        }
        CloseableHttpResponse closeableHttpResponse = webClient.get(location);
        webClient.printResponse(closeableHttpResponse);
        webClient.close();
    }
}
