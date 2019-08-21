package com.lmm.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: arno.yan
 * @Date: 2019/3/22
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String path = SQLUtil.class.getResource("/").getPath();

        BufferedReader in = new BufferedReader(new FileReader(path + "source.text"));
        String str = in.readLine();

        String[] strArray = str.split(",");

        int count = 0;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < strArray.length; i++) {
            list.add(strArray[i]);
            if (i != 0 && i % 500 == 0) {
                String stl = StringUtils.join(list, ",");
                BufferedWriter out = new BufferedWriter(new FileWriter(path + "split" + (count++) + ".txt"));
                out.write(stl);
                out.flush();
                list.clear();
            }
        }

        String stl = StringUtils.join(list, ",");
        BufferedWriter out = new BufferedWriter(new FileWriter(path + "split" + (count++) + ".txt"));
        out.write(stl);
        out.flush();
        list.clear();
    }
}
