package com.lmm.jdk8.demo;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: arno.yan
 * @Date: 2020/10/21
 */
public class ExcelDemo1 {

    public static void main(String[] args) {
        // 读取 excel 表格的路径
        String test1 = "/Users/xmly/test1.xlsx";
        String test2 = "/Users/xmly/test2.xlsx";
        String test3 = "/Users/xmly/test4.xlsx";


        try {
            // sheetNo --> 读取哪一个 表单
            // headLineMun --> 从哪一行开始读取( 不包括定义的这一行，比如 headLineMun为2 ，那么取出来的数据是从 第三行的数据开始读取 )
            // clazz --> 将读取的数据，转化成对应的实体，需要 extends BaseRowModel

            Map<String, String> noToPrice = new HashMap<>();
            for (int i = 2; i <= 9; i++) {
                Sheet sheet = new Sheet(i, 1);

                // 这里 取出来的是 ExcelModel实体 的集合
                List<Object> readList = EasyExcelFactory.read(new FileInputStream(test1), sheet);

                for (Object obj : readList) {
                    List<Object> list = (List) obj;
                    if (CollectionUtils.isNotEmpty(list) && list.size() > 8) {

                        Object key = list.get(3);
                        Object value = list.get(7);

                        if (key != null && value != null) {
                            noToPrice.put(key.toString(), value.toString());
                        }
                    }
                }
            }


            ExcelWriter excelWriter = EasyExcelFactory.getWriter(new FileOutputStream(test3), ExcelTypeEnum.XLSX, true);

            for (int i = 1; i <= 5; i++) {
                Sheet sheet = new Sheet(i, 1);

                // 这里 取出来的是 ExcelModel实体 的集合

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

                excelWriter.write1(readList, sheet);
            }

            excelWriter.finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
