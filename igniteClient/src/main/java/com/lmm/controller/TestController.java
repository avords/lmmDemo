package com.lmm.controller;

import com.alibaba.fastjson.JSON;
import com.lmm.ignite.User;
import com.lmm.util.IgniteCacheUtils;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arno.yan on 2018/12/29.
 */
@Controller
public class TestController {
    
    @Autowired
    private IgniteCacheUtils igniteCacheUtils;
    
    @RequestMapping("/ignite/save")
    @ResponseBody
    public String save(){
        for(Long i = 0L; i<1001L; i++){
            User user = new User(i, "雷涛"+i, "514721"+i, "西安电子科技大学"+i, 45);
            igniteCacheUtils.getIgniteCache("test_user").put(i, user);
        }

        return "success";
    }

    @RequestMapping("/ignite/get/{id}")
    @ResponseBody
    public String get(@PathVariable Long id){
        return JSON.toJSONString(igniteCacheUtils.getIgniteCache("test_user").get(id));
    }

    @RequestMapping("/ignite/sql/{id}")
    @ResponseBody
    public String sql(@PathVariable Long id){

        SqlFieldsQuery sql = new SqlFieldsQuery("select * from User where id=?")
                .setArgs(id);

        QueryCursor cursor = igniteCacheUtils.getIgniteCache("test_user").query(sql);
        
        List list = new ArrayList<>();
        cursor.forEach(s->list.add(s));
        
        return JSON.toJSONString(list);
    }
}
