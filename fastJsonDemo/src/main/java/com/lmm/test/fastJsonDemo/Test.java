package com.lmm.test.fastJsonDemo;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arno.yan on 2018/11/6.
 */
public class Test {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        
        for(int i=0;i<20;i++){
            list.add(i);
        }
        
        int i=0;
        while (true){
            i++;
            list = list.subList(0,2);
            list.add(3);
            list.add(4);
            System.out.println("第"+i+"次"+ JSON.toJSONString(list));
        }
    }
}
