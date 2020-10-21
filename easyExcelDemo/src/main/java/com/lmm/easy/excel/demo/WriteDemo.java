package com.lmm.easy.excel.demo;

import com.alibaba.excel.EasyExcel;
import com.lmm.easy.excel.entity.DataDemo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author: arno.yan
 * @Date: 2020/10/21
 */
public class WriteDemo {

    public static void main(String[] args) {
        String path = PathUtils.getRootPath() + "demo1.xlsx";

        List<DataDemo> dataDemos = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            DataDemo dataDemo = new DataDemo();
            dataDemo.setDate(new Date());
            dataDemo.setDoubleData(new Random().nextDouble());
            dataDemo.setString("雷涛" + i);
            dataDemos.add(dataDemo);
        }
        EasyExcel.write(path, DataDemo.class).sheet().doWrite(dataDemos);
    }
}
