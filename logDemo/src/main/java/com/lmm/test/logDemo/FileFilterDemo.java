package com.lmm.test.logDemo;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Administrator on 2016/11/7.
 */
public class FileFilterDemo {
    public static void main(String[] args) {
        File dir = new File("/an/dir/");
        FileFilter directoryFilter = (File f) -> f.isDirectory();
        File[] dirs = dir.listFiles(directoryFilter);
    }
}
