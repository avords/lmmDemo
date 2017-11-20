package com.lmm.test.fastJsonDemo;

import com.lmm.test.fastJsonDemo.vo1.BuyInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */
public class BuyInfoDemo {
    public static void main(String[] args) {
        BuyInfo buyInfo = new BuyInfo();
        List<com.lmm.test.fastJsonDemo.vo1.Schoole> schooles = new ArrayList<>();
        com.lmm.test.fastJsonDemo.vo1.Schoole schoole = new com.lmm.test.fastJsonDemo.vo1.Schoole();
        schoole.setDizhi("sss");
        schoole.setName("dd");
        schooles.add(schoole);
        buyInfo.setSchooles(schooles);

        com.lmm.test.fastJsonDemo.vo2.BuyInfo buyInfo1 = VoCopyDemo.copyProperties(buyInfo, com.lmm.test.fastJsonDemo.vo2.BuyInfo.class);
        Schoole schoole1 = (Schoole) buyInfo1.getSchooles().get(0);
        System.out.println(schoole1.getName());
    }
}
