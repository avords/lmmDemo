package com.lmm.controller;

import com.alibaba.fastjson.JSON;
import com.lmm.constants.IgniteConstants;
import com.lmm.ignite.User;
import com.lmm.util.IgniteCacheUtils;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arno.yan on 2018/12/29.
 */
@Controller
@RequestMapping("/ignite")
public class IgniteController {
    
    @Autowired
    private IgniteCacheUtils igniteCacheUtils;

    @RequestMapping("/user/show")
    public String show(){
        return "show";
    }
    
    @RequestMapping("/user/save")
    @ResponseBody
    public String saveUser(User user){
        
        igniteCacheUtils.getIgniteCache(IgniteConstants.SCHEMA_USER).put(user.getId(), user);

        return "success";
    }

    @RequestMapping("/user")
    @ResponseBody
    public String get(@RequestParam Long id){
        return JSON.toJSONString(igniteCacheUtils.getIgniteCache(IgniteConstants.SCHEMA_USER).get(id));
    }

    @RequestMapping("/user/sql")
    @ResponseBody
    public String sql(@RequestParam String sql){

        SqlFieldsQuery sqlFieldsQuery = new SqlFieldsQuery(sql);

        QueryCursor cursor = igniteCacheUtils.getIgniteCache(IgniteConstants.SCHEMA_USER).query(sqlFieldsQuery);
        
        List list = new ArrayList<>();
        cursor.forEach(s->list.add(s));
        
        return JSON.toJSONString(list);
    }
}
