package com.lmm.test.fastJsonDemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.springframework.util.Assert;

/**
 * Created by arno on 2016/11/21.
 */
public class BeanUtils {

    /**
     * 复制
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source,Class<T> clazz){
        Assert.notNull(source);
        String jsonStr = JSON.toJSONString(source, SerializerFeature.DisableCircularReferenceDetect);
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * 复制泛型
     * @param source
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source,TypeReference<T> type){
        Assert.notNull(source);
        String jsonStr = JSON.toJSONString(source, SerializerFeature.DisableCircularReferenceDetect);
        return JSON.parseObject(jsonStr,type);
    }

    /**
     * 过滤熟悉复制
     * @param source
     * @param filterKeys
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source,String filterKeys ,Class<T> clazz){
        Assert.notNull(source);
        String jsonStr = JSON.toJSONString(source, new SimplePropertyPreFilter(filterKeys), SerializerFeature.DisableCircularReferenceDetect);
        return JSON.parseObject(jsonStr,clazz);
    }
    
}