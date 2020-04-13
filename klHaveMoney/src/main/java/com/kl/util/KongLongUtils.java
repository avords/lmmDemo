package com.kl.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kl.factory.RestTemplateFactory;
import com.kl.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * @author: arno.yan
 * @Date: 2020/4/6
 */
public class KongLongUtils {

    private static final Logger logger = LoggerFactory.getLogger(KongLongUtils.class);

    private static final String serverUrl = "http://apiv2.higaoyao.com:9527";

    public static int queryBalance(String token, String ip) {
        RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);

        //headers.setAccept(Collections.singletonList(MediaType.parseMediaType("text/html;charset=UTF-8")));
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.add("Authorization", "Bearer " + token);
        headers.add("app_type", "0");
        headers.add("channel", "YYB");
        headers.add("version", "1.0.51");
        headers.add("versionCode", "53");
        headers.add("platform", "android");
        headers.add("Host", "apiv2.higaoyao.com:9527");
        headers.add("Connection", "Keep-Alive");
        headers.add("Accept-Encoding", "gzip");
        headers.add("User-Agent", "okhttp/3.12.0");
        headers.add("X-Forwarded-For", ip);
        headers.add("X-Real-IP", ip);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(null, headers);
        // 执行HTTP请求
        ResponseEntity<String> response = restTemplate.exchange(serverUrl + "/app/api/v1/user/baseInfo", HttpMethod.GET, requestEntity, String.class);

        ResponseVo responseVo = JSONObject.parseObject(response.getBody(), ResponseVo.class);

        if (responseVo.getCode() != 200) {
            return 0;
        }
        return JSON.parseObject(responseVo.getData()).getInteger("balance");
    }

    public static int lookVideo(String token, String ip) {
        RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);

        //headers.setAccept(Collections.singletonList(MediaType.parseMediaType("text/html;charset=UTF-8")));
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.add("Authorization", "Bearer " + token);
        headers.add("Origin", "http://dragon.higaoyao.com");
        headers.add("Referer", "http://dragon.higaoyao.com/v80/index.html?version=1.1.1&code=61&platform=android");
        headers.add("Host", "apiv2.higaoyao.com:9527");
        headers.add("Accept-Encoding", "gzip, deflate");
        headers.add("Accept-Language", "zh-CN,en-US;q=0.9");
        headers.add("User-Agent", "Mozilla/5.0 (Linux; Android 10; BKL-AL20 Build/HUAWEIBKL-AL20; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.126 MQQBrowser/6.2 TBS/045132 Mobile Safari/537.36");
        headers.add("X-Requested-With", "com.yiimuu.rich_dinosaur");
        headers.add("X-Forwarded-For", ip);
        headers.add("X-Real-IP", ip);

        HttpEntity<String> requestEntity = new HttpEntity<>("{\"awardType\":1}", headers);
        // 执行HTTP请求
        ResponseEntity<String> response = restTemplate.exchange(serverUrl + "/api/v1/award/video/look", HttpMethod.POST, requestEntity, String.class);

        ResponseVo responseVo = JSONObject.parseObject(response.getBody(), ResponseVo.class);
        if (responseVo.getCode() == 400) {
            return 0;
        }

        try {
            return JSON.parseObject(responseVo.getData()).getInteger("lastVideoAwardNum");
        } catch (Exception e) {
            logger.error("token:{}, ip:{} have error!, code:{},msg:{},data:{}", token, ip, responseVo.getCode(), responseVo.getMsg(), responseVo.getData());
            throw new RuntimeException(e);
        }
    }

    public static int getRewards(String token, String ip) {
        RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);

        //headers.setAccept(Collections.singletonList(MediaType.parseMediaType("text/html;charset=UTF-8")));
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-Length", "15");
        headers.add("Origin", "http://dragon.higaoyao.com");
        headers.add("Referer", "http://dragon.higaoyao.com/v80/index.html?version=1.0.51&code=53&platform=android");
        headers.add("Host", "apiv2.higaoyao.com:9527");
        headers.add("Accept-Encoding", "gzip, deflate");
        headers.add("Accept-Language", "zh-CN,en-US;q=0.9");
        headers.add("User-Agent", "Mozilla/5.0 (Linux; Android 10; BKL-AL20 Build/HUAWEIBKL-AL20; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.126 MQQBrowser/6.2 TBS/045132 Mobile Safari/537.36");
        headers.add("X-Requested-With", "com.yiimuu.rich_dinosaur");
        headers.add("X-Forwarded-For", ip);
        headers.add("X-Real-IP", ip);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(null, headers);
        // 执行HTTP请求
        ResponseEntity<String> response = restTemplate.exchange(serverUrl + "/api/v1/award/rightGold", HttpMethod.POST, requestEntity, String.class);
        return 0;
    }

    public static int daySign(String token, String ip) {
        RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(type);

        //headers.setAccept(Collections.singletonList(MediaType.parseMediaType("text/html;charset=UTF-8")));
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.add("Authorization", "Bearer " + token);
        headers.add("app_type", "0");
        headers.add("channel", "YYB");
        headers.add("version", "1.0.51");
        headers.add("versionCode", "53");
        headers.add("platform", "android");
        headers.add("Host", "apiv2.higaoyao.com:9527");
        headers.add("Connection", "Keep-Alive");
        headers.add("Accept-Encoding", "gzip");
        headers.add("User-Agent", "okhttp/3.12.0");
        headers.add("X-Forwarded-For", ip);
        headers.add("X-Real-IP", ip);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(null, headers);
        // 执行HTTP请求
        ResponseEntity<String> response = restTemplate.exchange(serverUrl + "/app/api/v1/task/daySign", HttpMethod.POST, requestEntity, String.class);
        ResponseVo responseVo = JSONObject.parseObject(response.getBody(), ResponseVo.class);

        if (responseVo.getCode() == 200
                || responseVo.getCode() == 400) {
            return 0;
        }
        
        logger.error("daySign have error, code:{}, msg:{}", responseVo.getCode(), responseVo.getMsg());
        throw new RuntimeException("daySign have error");
    }

    public static int syncTime(String token, String ip) {
        RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);

        //headers.setAccept(Collections.singletonList(MediaType.parseMediaType("text/html;charset=UTF-8")));
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-Length", "15");
        headers.add("Origin", "http://dragon.higaoyao.com");
        headers.add("Referer", "http://dragon.higaoyao.com/v80/index.html?version=1.0.51&code=53&platform=android");
        headers.add("Host", "apiv2.higaoyao.com:9527");
        headers.add("Accept-Encoding", "gzip, deflate");
        headers.add("Accept-Language", "zh-CN,en-US;q=0.9");
        headers.add("User-Agent", "Mozilla/5.0 (Linux; Android 10; BKL-AL20 Build/HUAWEIBKL-AL20; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.126 MQQBrowser/6.2 TBS/045132 Mobile Safari/537.36");
        headers.add("X-Requested-With", "com.yiimuu.rich_dinosaur");
        headers.add("X-Forwarded-For", ip);
        headers.add("X-Real-IP", ip);

        HttpEntity<String> requestEntity = new HttpEntity<>("{}", headers);
        // 执行HTTP请求
        ResponseEntity<String> response = restTemplate.exchange(serverUrl + "/api/v1/home/syncTime", HttpMethod.POST, requestEntity, String.class);
        ResponseVo responseVo = JSONObject.parseObject(response.getBody(), ResponseVo.class);

        if (responseVo.getCode() == 200) {
            return 0;
        }
        throw new RuntimeException("syncTime have error");
    }

    public static void main(String[] args) {
        daySign("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJkZXYiLCJpYXQiOjE1ODYxNzAzMzAsImV4cCI6MTU4ODc2MjMzMCwibmJmIjoxNTg2MTcwMzMwLCJ1aWQiOjI2MTgxNTQzfQ.IgZ9EuB3jOCH7Eg2aDO0aN6OHRNfqs5_kNYpJI9D2Dg", IPUtils.generateIP());
        syncTime("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJkZXYiLCJpYXQiOjE1ODYxNzAzMzAsImV4cCI6MTU4ODc2MjMzMCwibmJmIjoxNTg2MTcwMzMwLCJ1aWQiOjI2MTgxNTQzfQ.IgZ9EuB3jOCH7Eg2aDO0aN6OHRNfqs5_kNYpJI9D2Dg", IPUtils.generateIP());
    }
}
