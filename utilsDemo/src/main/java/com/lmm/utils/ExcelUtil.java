package com.lmm.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

public class ExcelUtil {

    private static final int WIDTH_MULT = 300;
    private static final int MIN_CHARS = 8;
    private static final int MIN_WIDTH = WIDTH_MULT * MIN_CHARS;
    private static final int MAX_ROWS = 65536;

    public static void exportExcel(HttpServletResponse response, List<Object[]> datas,
                                   String[] titles, String exportName) throws Exception {

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + exportName);
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));

        OutputStream outputStream = response.getOutputStream();
        createExcelFile(datas, outputStream, titles);
    }

    public static int createExcelFile(List<Object[]> datas, OutputStream outputStream, String[] titles) throws Exception {
        HSSFWorkbook wb;
        try {
            wb = new HSSFWorkbook();

            createBody(wb, datas, titles);
            wb.write(outputStream);
        } catch (Exception e) {
            throw e;
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }

        }
        return datas.size();
    }

    private static void createBody(HSSFWorkbook wb, List<Object[]> dataList, String[] titles) {
        //创建第一个sheet
        int rowIndex = 0;
        HSSFSheet sheet = wb.createSheet();
        sheet.setAutobreaks(true);
        createTitle(sheet, rowIndex, titles);

        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }

        for (int i = 0, n = dataList.size(); i < n; i++) {

            if (MAX_ROWS - 1 == rowIndex) {
                //超过max_rows新增一个sheet
                rowIndex = 0;
                sheet = wb.createSheet();
                sheet.setAutobreaks(true);
                createTitle(sheet, rowIndex, titles);
            }


            HSSFRow hssfRow = sheet.createRow(++rowIndex);

            Object[] objects = dataList.get(i);

            if (objects == null || objects.length == 0) {
                continue;
            }

            for (int j = 0; j < objects.length; j++) {
                Object obj = objects[j];
                if (obj == null) {
                    obj = "";
                }
                createCell(sheet, hssfRow, j, obj.toString());
            }
        }
    }

    private static void createTitle(HSSFSheet sheet, int rowIndex, String[] titles) {
        HSSFRow hssfRow = sheet.createRow(rowIndex);

        if (titles == null || titles.length == 0) {
            return;
        }
        for (int j = 0; j < titles.length; j++) {
            createCell(sheet, hssfRow, j, titles[j]);
        }
    }

    private static void createCell(HSSFSheet sheet, HSSFRow hssfRow, int cellNum, String value) {
        HSSFCell hssfCell = hssfRow.createCell(cellNum);
        fixWidthAndPopulate(sheet, hssfCell, value);
    }

    private static void fixWidthAndPopulate(HSSFSheet sheet, HSSFCell cell, String value) {
        cell.setCellValue(value);
        int valWidth = value.length() * WIDTH_MULT;
        if (valWidth < MIN_WIDTH) {
            valWidth = MIN_WIDTH;
        }

        if (valWidth > sheet.getColumnWidth(cell.getColumnIndex())) {
            sheet.setColumnWidth(cell.getColumnIndex(), (short) valWidth);
        }
    }
}
