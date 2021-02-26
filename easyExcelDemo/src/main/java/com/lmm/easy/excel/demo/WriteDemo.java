package com.lmm.easy.excel.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import com.lmm.easy.excel.entity.DataDemo;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.Arrays;
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

        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(path, DataDemo.class).build();
            // 把sheet设置为不需要头 不然会输出sheet的头 这样看起来第一个table 就有2个头了
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").needHead(Boolean.FALSE).build();
            // 这里必须指定需要头，table 会继承sheet的配置，sheet配置了不需要，table 默认也是不需要
            WriteTable writeTable0 = EasyExcel.writerTable(0).needHead(Boolean.FALSE).build();
            AbstractRowWriteHandler loopMergeStrategy = new AbstractRowWriteHandler() {
                @Override
                public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                                            Integer relativeRowIndex, Boolean isHead) {
                    if (isHead) {
                        return;
                    }
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(row.getRowNum(), row.getRowNum(),
                            0, 3);//0到3列合并为一个单元格
                    writeSheetHolder.getSheet().addMergedRegionUnsafe(cellRangeAddress);
                }
            };
            List<WriteHandler> list = new ArrayList<>();
            list.add(loopMergeStrategy);
            writeTable0.setCustomWriteHandlerList(list);

            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            WriteFont contentWriteFont = new WriteFont();
            contentWriteFont.setFontHeightInPoints((short) 12);
            contentWriteFont.setColor(Font.COLOR_RED);
            contentWriteCellStyle.setWriteFont(contentWriteFont);
            contentWriteCellStyle.setWrapped(true);
            contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
            HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                    new HorizontalCellStyleStrategy(null, contentWriteCellStyle);
            list.add(horizontalCellStyleStrategy);

            //设置行高和列宽
            SimpleRowHeightStyleStrategy simpleRowHeightStyleStrategy = new SimpleRowHeightStyleStrategy(null, (short) 50);
            list.add(simpleRowHeightStyleStrategy);
            
            WriteTable writeTable1 = EasyExcel.writerTable(1).needHead(Boolean.TRUE).build();
            // 第一次写入会创建头
            DataDemo dataDemo = new DataDemo();
            dataDemo.setString("提示：订单列表中包含售后成功的数据，请筛选【售后状态】- 【无售后】以及【售后失败】的订单安排发货。【售后中】的数据请先处理对应售后申请后再视情况安排发货");
            List<DataDemo> list1 = Arrays.asList(dataDemo);


            excelWriter.write(list1, writeSheet, writeTable0);
            // 第二次写如也会创建头，然后在第一次的后面写入数据
            excelWriter.write(dataDemos, writeSheet, writeTable1);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
