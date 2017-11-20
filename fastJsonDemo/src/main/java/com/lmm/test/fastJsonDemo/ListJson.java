package com.lmm.test.fastJsonDemo;

import com.alibaba.fastjson.JSON;
import com.lmm.test.fastJsonDemo.vo1.Schoole;
import com.lmm.test.fastJsonDemo.vo1.User;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/24.
 */
public class ListJson {
    public static void main(String[] args) {
        User user = new User();
        user.setName("李世民");
        user.setAge(1562);
        user.setSchooles(new ArrayList<Schoole>());
        System.out.println(JSON.toJSONString(user));
    }
}
