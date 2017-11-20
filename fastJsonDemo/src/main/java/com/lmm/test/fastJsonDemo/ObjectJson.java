package com.lmm.test.fastJsonDemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lmm.test.fastJsonDemo.vo1.Schoole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
public class ObjectJson {
    public static void main(String[] args) {
        List<Schoole> list = new ArrayList<>();
        Schoole schoole = new Schoole();
        schoole.setDizhi("7456");
        Schoole schoole1 = new Schoole();
        schoole1.setDizhi("123");
        Schoole schoole2 = new Schoole();
        schoole2.setDizhi("891");
        
        list.add(schoole);
        list.add(schoole1);
        list.add(schoole2);
        String json = JSON.toJSONString(list);
        System.out.println(json);
        
        Object obj = JSON.parse(json);
        System.out.println(obj instanceof List);

        JSONArray list1 = (JSONArray)obj;
        JSONObject jsonObject = (JSONObject) list1.get(0);
        System.out.println(jsonObject.get("dizhi"));
        
        List<Schoole> list2 = (List)obj;//会报错
        System.out.println(list2.get(0).getDizhi());
    }
}
