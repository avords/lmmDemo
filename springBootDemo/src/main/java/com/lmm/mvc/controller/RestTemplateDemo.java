package com.lmm.mvc.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class RestTemplateDemo {
    public static void main(String[] args) {
        String orderNos = "2018051612460140400078048641,2018051512460170000077565192,2018051512460126400077423486,2018052012460108100079469242,2018051712460137300078426922,2018051612460116900078081216,2018051812460193300078810263,2018051412460179400077251970,2018051812460128400078706507,2018051712460195400078687665,2018051812460190700078788653,2018051612460165500078033722,2018051512460194900077538552,2018051512460147800077432535,2018051412460115200077417386,2018051612460160400078168420,2018051512460173500077552008,2018051912460138300079145267,2018051412460162200077300419,2018051412460192200077418242,2018052112460173000079867954,2018051612460144000077957957,2018051512460147900077473789,2018052012460156800079689909,2018052012460115800079832756,2018051912460136600079087565,2018051812460169000079000418,2018052112460148800079870505,2018051512460180400077569420,2018051612460121900078128301,2018051512460195200077576058";
        String[] orderArray = orderNos.split(",");
        AtomicInteger count = new AtomicInteger();
        String url = "http://ops.ximalaya.com/business-trade-admin-web/refund/submit";
        RestTemplate client = new RestTemplate();
        client.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        Arrays.stream(orderArray).forEach(s -> {
            HttpHeaders headers = new HttpHeaders();
            //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
            MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
            headers.setContentType(type);
            
            //headers.setAccept(Collections.singletonList(MediaType.parseMediaType("text/html;charset=UTF-8")));
            headers.setAccept(Collections.singletonList(MediaType.ALL));
            
            //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
            MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
            //  也支持中文
            params.add("merchantOrderNo", s);
            params.add("businessTypeId", "1246");
            params.add("userId", "70702075");
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
            // 执行HTTP请求
            ResponseEntity<String> response = client.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if("退款请求已提交，请再次查询订单状态以核实退款成功".equals(response.getBody())){
                count.getAndIncrement();
            }
        });

        System.out.println(count);
    }
}
