package com.lmm.cache;

import java.util.Date;

/**
 * Created by Administrator on 2016/12/2.
 */
public interface CacheService {
    /**
     * 当key不存在是添加缓存
     * @param key
     * @param value
     * @return
     */
    boolean add(String key, Object value);

    /**
     * 当key不存在是添加缓存，并且设置过期时间
     * @param key
     * @param value
     * @param expiry
     * @return
     */
    boolean add(String key, Object value, Date expiry);

    /**
     * 删除key
     * @param key
     * @return
     */
    boolean delete(String key);

    /**
     * 得到key的value
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 当key存在时，替换key的value
     * @param key
     * @param value
     * @return
     */
    boolean replace(String key, Object value);

    /**
     * 当key存在时，替换key的value,并且设置有效时间
     * @param key
     * @param value
     * @param expiry
     * @return
     */
    boolean replace(String key, Object value, Date expiry);

    /**
     * 不管key存不存在都设置key,是add和replace的合体
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, Object value);

    /**
     * 不管key存不存在都设置key,是add和replace的合体,并且设置过期时间
     * @param key
     * @param value
     * @param expiry
     * @return
     */
    boolean set(String key, Object value, Date expiry);

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    boolean keyExists(String key);
}
