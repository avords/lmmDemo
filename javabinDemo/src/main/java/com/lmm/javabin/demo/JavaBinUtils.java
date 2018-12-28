package com.lmm.javabin.demo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */
public class JavaBinUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaBinUtils.class);

    /**
     * java调用操作系统的命令
     *
     * @param cmd  命令集合
     * @param path 路径
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static int runBin(List<String> cmd, String path) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder();
        //设置程序工作目录
        if (StringUtils.isNotBlank(path)) {
            pb.directory(new File(path));
        }
        pb.command(cmd);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        StringBuilder result = new StringBuilder();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
                LOGGER.info(pb.command().toString() + " --->: " + line);
            }
        } catch (IOException e) {
            LOGGER.warn("failed to read output from process", e);
        } finally {
            IOUtils.closeQuietly(reader);
        }
        process.waitFor();
        int exit = process.exitValue();
        if (exit != 0) {
            throw new IOException("failed to execute:" + pb.command() + " with result:" + result);
        } else {
            return exit;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        List cmd = new ArrayList();
        cmd.add("calc");
        runBin(cmd, "c:/");
    }
}
