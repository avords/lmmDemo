package com.lmm.dbDemo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yandongquan on 14-11-10.
 */
public class PropertiesUtil {
	static{
		PropertiesUtil.getInstance();
	}

    private static Properties properties;

    private static volatile PropertiesUtil instance = null;

    private void init() {
        InputStream input = null;
        try {
            properties = new Properties();
            input = getClass().getResourceAsStream("/jdbc.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(input != null){
                try{
                    input.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        // ResourceBundle.getBundle("const");
    }

    private PropertiesUtil() {
        init();
    }

    public static PropertiesUtil getInstance() {
        if (instance == null) {
            synchronized (PropertiesUtil.class) {
                if (instance == null) {
                    instance = new PropertiesUtil();
                }
            }
        }
        return instance;
    }

    public static String getValue(String key){
        String value = "";
        if(properties != null){
            value = properties.getProperty(key);
        }

        return value;
    }

    public static void main(String[] args){
        PropertiesUtil.getInstance();
        getValue("topHead");
    }
}
