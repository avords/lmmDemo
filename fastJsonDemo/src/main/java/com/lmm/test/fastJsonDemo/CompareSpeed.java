package com.lmm.test.fastJsonDemo;

import com.alibaba.fastjson.TypeReference;
import com.lmm.test.fastJsonDemo.vo1.Schoole;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */
public class CompareSpeed {
    public static void main(String[] args) throws Exception {
        List<Schoole> schooles = new ArrayList<Schoole>();
        for(int i=0;i<1000000;i++){
            Schoole schooleTemp = new Schoole();
            schooleTemp.setName("渭南师范"+i);
            schooleTemp.setDizhi("西安"+i);
            schooles.add(schooleTemp);
        }
        long start1 = System.currentTimeMillis();
        //apache BeanUtils
        List<com.lmm.test.fastJsonDemo.vo2.Schoole> schooles1 = new ArrayList<>(schooles.size());
        for(Schoole schoole:schooles){
            com.lmm.test.fastJsonDemo.vo2.Schoole schooleTemp = new com.lmm.test.fastJsonDemo.vo2.Schoole();
            BeanUtils.copyProperties(schooleTemp,schoole);
            schooles1.add(schooleTemp);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("apache copy耗时"+(end1-start1));

        long start2 = System.currentTimeMillis();
        //spring BeanUtils
        List<com.lmm.test.fastJsonDemo.vo2.Schoole> schooles2 = new ArrayList<>(schooles.size());
        for(Schoole schoole:schooles){
            com.lmm.test.fastJsonDemo.vo2.Schoole schooleTemp = new com.lmm.test.fastJsonDemo.vo2.Schoole();
            org.springframework.beans.BeanUtils.copyProperties(schoole,schooleTemp);
            schooles2.add(schooleTemp);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("spring copy耗时"+(end2-start2));

        long start3 = System.currentTimeMillis();
        //fastJson BeanUtils 批量
        ArrayList<com.lmm.test.fastJsonDemo.vo2.Schoole> list = VoCopyDemo.copyProperties(schooles,new TypeReference<ArrayList<com.lmm.test.fastJsonDemo.vo2.Schoole>>(){});
        long end3 = System.currentTimeMillis();
        System.out.println("fastJson copy 批量耗时"+(end3-start3));

        long start4 = System.currentTimeMillis();
        //fast Json BeanUtils
        List<com.lmm.test.fastJsonDemo.vo2.Schoole> schooles4 = new ArrayList<>(schooles.size());
        for(Schoole schoole:schooles){
            com.lmm.test.fastJsonDemo.vo2.Schoole schooleTemp = VoCopyDemo.copyProperties(schoole, com.lmm.test.fastJsonDemo.vo2.Schoole.class);
            schooles4.add(schooleTemp);
        }
        long end4 = System.currentTimeMillis();
        System.out.println("fastJson copy 单个耗时"+(end4-start4));
    }
}
