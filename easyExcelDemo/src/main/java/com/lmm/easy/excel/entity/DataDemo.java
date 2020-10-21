package com.lmm.easy.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.lmm.easy.excel.conver.CustomStringStringConverter;

import java.util.Date;

/**
 * @author: arno.yan
 * @Date: 2020/10/21
 */
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
public class DataDemo {

    /**
     * 我想所有的 字符串起前面加上"自定义："三个字
     */
    @ExcelProperty(value = {"我的表格", "姓名"}, converter = CustomStringStringConverter.class)
    private String string;
    /**
     * 我想写到excel 用年月日的格式
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty(value = {"我的表格", "日期"})
    private Date date;
    /**
     * 我想写到excel 用百分比表示
     */
    @NumberFormat("#.##%")
    @ExcelProperty(value = {"我的表格", "数字标题"})
    private Double doubleData;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(Double doubleData) {
        this.doubleData = doubleData;
    }
}
