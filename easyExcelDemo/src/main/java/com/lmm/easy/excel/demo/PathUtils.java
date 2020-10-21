package com.lmm.easy.excel.demo;

/**
 * @author: arno.yan
 * @Date: 2020/10/21
 */
public class PathUtils {
    public static String getRootPath(){
        return PathUtils.class.getClassLoader().getResource("").getPath();
    }

    public static void main(String[] args) {
        System.out.println(getRootPath());
    }
}
