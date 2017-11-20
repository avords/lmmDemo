package com.lmm.test.encryptDemo;

import com.lmm.test.encryptDemo.vo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/13.
 */
public class FastJsonDemo {
    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            User user = new User();
            user.setName("guoxiaotian"+i);
        }
    }
}
