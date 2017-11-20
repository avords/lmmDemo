package com.lmm.test.fastJsonDemo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/1.
 */
public class ListMapDemo {
    public static void main(String[] args) {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap();
        map.put("name","leitao");
        map.put("school","zhongxin");
        for(int i=0;i<10;i++){
            list.add(map);
        }
        String json = JSONArray.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(json);
    }
}
