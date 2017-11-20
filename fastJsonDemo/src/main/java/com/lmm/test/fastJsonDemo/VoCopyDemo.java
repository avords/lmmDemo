package com.lmm.test.fastJsonDemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lmm.test.fastJsonDemo.vo1.Schoole;
import com.lmm.test.fastJsonDemo.vo1.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/21.
 */
public class VoCopyDemo {
    public static void main(String[] args) {
        User user1 = new User();
        user1.setName("雷涛");
        user1.setAge(30);
        user1.setBirthday(new Date());
        
        Schoole schoole = new Schoole();
        schoole.setName("北方民族大学");
        schoole.setDizhi("宁夏回族自治州");
        user1.setSchoole(schoole);
        List<Schoole> schooles = new ArrayList<Schoole>();
        for(int i=0;i<100;i++){
            Schoole schooleTemp = new Schoole();
            schooleTemp.setName("渭南师范"+i);
            schooleTemp.setDizhi("西安"+i);
            schooles.add(schooleTemp);
        }
        user1.setSchooles(schooles);
        com.lmm.test.fastJsonDemo.vo2.User user2 = copyProperties(user1, com.lmm.test.fastJsonDemo.vo2.User.class);
        System.out.println("user2"+user2.getSchooles().get(0).getName());
    }
    
    public static <T> T copyProperties(Object source,Class<T> clazz){
        String jsonStr = JSON.toJSONString(source);
        return JSON.parseObject(jsonStr,clazz);
    }
    
    public static <T> T copyProperties(Object source,TypeReference<T> type){
        String jsonStr = JSON.toJSONString(source);
        return JSON.parseObject(jsonStr,type);
    }
}
