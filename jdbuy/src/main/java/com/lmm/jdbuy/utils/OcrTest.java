package com.lmm.jdbuy.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/9.
 */
public class OcrTest {
    public static void main(String[] args) {
        String path = "d:\\jd.png";
        System.out.println("ORC Test Begin......");
        try {
            String valCode = new OCRHelper().recognizeText(new File(path));
            System.out.println(valCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ORC Test End......");
    }
}
