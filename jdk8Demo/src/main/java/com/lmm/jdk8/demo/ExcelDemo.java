package com.lmm.jdk8.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: arno.yan
 * @Date: 2020/10/21
 */
public class ExcelDemo {

    public static void main(String[] args) {
        // 读取 excel 表格的路径
        String test1 = "/Users/xmly/test1.xlsx";
        String test2 = "/Users/xmly/test2.xlsx";
        String test3 = "/Users/xmly/test3.xlsx";


        try {
            // sheetNo --> 读取哪一个 表单
            // headLineMun --> 从哪一行开始读取( 不包括定义的这一行，比如 headLineMun为2 ，那么取出来的数据是从 第三行的数据开始读取 )
            // clazz --> 将读取的数据，转化成对应的实体，需要 extends BaseRowModel

            Map<String, String> noToPrice = new HashMap<>();
            for (int i = 2; i <= 9; i++) {

                // 这里 取出来的是 ExcelModel实体 的集合
                List<Map<Integer, String>> readList = EasyExcel.read(new FileInputStream(test1)).sheet(i).doReadSync();

                for (Map<Integer, String> map : readList) {
                    String key = map.get(3);
                    String value = map.get(7);
                    if (MapUtils.isNotEmpty(map) && map.size() > 8 && StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                        noToPrice.put(key, value);
                    }
                }
            }

            ExcelWriter excelWriter = EasyExcel.write(test3).build();

            for (int i = 1; i <= 5; i++) {
                // 这里 取出来的是 ExcelModel实体 的集合
                Sheet sheet = new Sheet(i, 1);
                List<Object> readList = EasyExcelFactory.read(new FileInputStream(test2), sheet);

                for (Object obj : readList) {
                    List<Object> list = (List) obj;
                    if (CollectionUtils.isNotEmpty(list) && list.size() > 8) {

                        Object key = list.get(3);
                        String newValue = noToPrice.get(key == null ? null : key.toString());
                        if (StringUtils.isNotBlank(newValue) && NumberUtils.isNumber(newValue)) {
                            list.set(7, newValue);
                        }
                    }

                }

                //EasyExcel.write(test3).withTemplate(test2).sheet(i).doWrite(readList);
                //EasyExcel.write(test3).sheet(i).doWrite(readList);
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
                excelWriter.write(readList, writeSheet);
            }

            excelWriter.finish();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
