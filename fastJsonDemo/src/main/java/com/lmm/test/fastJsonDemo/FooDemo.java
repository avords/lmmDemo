package com.lmm.test.fastJsonDemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lmm.test.fastJsonDemo.vo1.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/21.
 */
public class FooDemo {
    public static void main(String[] args) {
        com.lmm.test.fastJsonDemo.vo1.Foo<User> foo1 = new com.lmm.test.fastJsonDemo.vo1.Foo<>();
        User user1 = new User();
        user1.setName("雷涛");
        foo1.setSchool(user1);
        com.lmm.test.fastJsonDemo.vo2.Foo<com.lmm.test.fastJsonDemo.vo2.User> foo = VoCopyDemo.copyProperties(foo1,new TypeReference<com.lmm.test.fastJsonDemo.vo2.Foo<com.lmm.test.fastJsonDemo.vo2.User>>(){});
        System.out.println(foo.getSchool().getName());

        List<User> list = new ArrayList<>();
        list.add(user1);
        for(int i=0;i<100000;i++){
            User userTemp = new User();
            userTemp.setName("雷涛"+i);
            list.add(userTemp);
        }
        ArrayList<com.lmm.test.fastJsonDemo.vo2.User> ss=VoCopyDemo.copyProperties(list,new TypeReference<ArrayList<com.lmm.test.fastJsonDemo.vo2.User>>(){});
        System.out.println(JSON.toJSONString(ss));
        System.out.println(ss.get(56).getName());
        System.out.println(ss.get(56) instanceof com.lmm.test.fastJsonDemo.vo2.User);
    }
}
