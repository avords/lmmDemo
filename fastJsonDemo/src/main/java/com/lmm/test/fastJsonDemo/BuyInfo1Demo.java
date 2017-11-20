package com.lmm.test.fastJsonDemo;

import com.alibaba.fastjson.JSON;
import com.lmm.test.fastJsonDemo.vo1.BuyInfo1;
import com.lmm.test.fastJsonDemo.vo1.Schoole;
import com.lmm.test.fastJsonDemo.vo1.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */
public class BuyInfo1Demo {
    public static void main(String[] args) {
        BuyInfo1 buyInfo1 = new BuyInfo1();
        List<User> user1s = new ArrayList<>();
        for(int i=0;i<10;i++){
            User user = new User();
            user.setAge(i);
            user.setName("雷涛"+i);
            List<Schoole> schooles = new ArrayList<>();
            for(int j=0;j<10;j++){
                Schoole schoole = new Schoole();
                schoole.setDizhi("西安"+i);
                schoole.setName("西工大"+i);
                schooles.add(schoole);
            }
            user.setSchooles(schooles);
            user1s.add(user);
        }
        buyInfo1.setUsers(user1s);
        com.lmm.test.fastJsonDemo.vo2.BuyInfo1 buyInfo11 = VoCopyDemo.copyProperties(buyInfo1, com.lmm.test.fastJsonDemo.vo2.BuyInfo1.class);
        System.out.println(JSON.toJSONString(buyInfo11));
        com.lmm.test.fastJsonDemo.vo2.User user = buyInfo11.getUsers().get(0);
        com.lmm.test.fastJsonDemo.vo2.Schoole schoole = user.getSchooles().get(0);
        System.out.println(user.getName());
        System.out.println(schoole.getName());
    }
}
