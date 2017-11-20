package com.lmm.test.fastJsonDemo;

import com.alibaba.fastjson.JSON;
import com.lmm.test.fastJsonDemo.vo1.Schoole;
import com.lmm.test.fastJsonDemo.vo1.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */
public class JsonToFile {
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
        for(int i=0;i<10000;i++){
            Schoole schooleTemp = new Schoole();
            schooleTemp.setName("渭南师范"+i);
            schooleTemp.setDizhi("西安"+i);
            schooles.add(schooleTemp);
        }
        user1.setSchooles(schooles);
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter("d:/java.json"));
            out.print(JSON.toJSONString(schooles));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
