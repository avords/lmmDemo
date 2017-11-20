package com.lmm.jdbuy.utils;

import org.jdesktop.swingx.util.OS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/12/9.
 */
public class OCRHelper {
    private final String LANG_OPTION = "-l";  //英文字母小写l，并非数字1    
    private final String EOL = System.getProperty("line.separator");
    private String tessPath = "D:\\Program Files\\Tesseract-OCR";//OCR程序安装目录
    /*public String recognizeText(InputStream inputStream, String imageFormat)throws Exception{
        File tempImage = ImageIOHelper.createImage(inputStream,imageFormat);
        return handleTempFile(tempImage);
    }*/
    public String recognizeText(File imageFile)throws Exception{
        //File tempImage = ImageIOHelper.createImage(imageFile,imageFormat);
        //图片去噪
        File tempImage = ClearImageHelper.cleanImage(imageFile);
        return handleTempFile(tempImage);
    }
    private String handleTempFile(File tempImage) throws Exception {
        File outputFile = new File(tempImage.getParentFile(),"output");
        StringBuffer strB = new StringBuffer();
        List cmd = new ArrayList();
        //配置环境变量到path
        //cmd.add("tesseract");
        if(OS.isWindowsXP()){
            cmd.add(tessPath+"\\tesseract");
        }else if(OS.isLinux()){
            cmd.add("tesseract");
        }else{
            cmd.add(tessPath+"\\tesseract");
        }
        cmd.add(tempImage.getAbsolutePath());//文件目录
        cmd.add(outputFile.getAbsolutePath());//输出目录
        //选择字库文件
        cmd.add(LANG_OPTION);
        cmd.add("chi_sim");


        ProcessBuilder pb = new ProcessBuilder();
        //设置程序工作目录
        pb.directory(tempImage.getParentFile());
        pb.command(cmd);
        pb.redirectErrorStream(true);

        Process process = pb.start();
        //tesseract.exe 1.jpg 1 -l chi_sim    
        int w = process.waitFor();

        //删除临时正在工作文件    
        //tempImage.delete();

        if(w==0){
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath()+".txt"),"UTF-8"));

            String str;
            while((str = in.readLine())!=null){
                strB.append(str).append(EOL);
            }
            in.close();
        }else{
            String msg;
            switch(w){
                case 1:
                    msg = "Errors accessing files.There may be spaces in your image's filename.";
                    break;
                case 29:
                    msg = "Cannot recongnize the image or its selected region.";
                    break;
                case 31:
                    msg = "Unsupported image format.";
                    break;
                default:
                    msg = "Errors occurred.";
            }
            tempImage.delete();
            throw new RuntimeException(msg);
        }
        new File(outputFile.getAbsolutePath()+".txt").delete();
        return strB.toString();
    }
}    
