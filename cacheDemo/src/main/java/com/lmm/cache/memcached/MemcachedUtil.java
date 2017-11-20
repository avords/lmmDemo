package com.lmm.cache.memcached;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.lmm.cache.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class MemcachedUtil implements CacheService {
    private static Object LOCK = new Object();
    private static final Logger log = LoggerFactory.getLogger(MemcachedUtil.class);
    private Properties properties;
    private MemCachedClient memCachedClient;

    private static MemcachedUtil instance;
    private MemcachedUtil(){
        init();
    }
    /**
     * 改用嵌套类静态实始化
     * @return
     */
    public static MemcachedUtil getInstance() {
        if(instance == null){
            synchronized(LOCK) {
                if (instance==null) {
                    instance=new MemcachedUtil();
                }
            }
        }
        return instance;
    }
    private void init() {
        InputStream inputStream = null;
        try {
            properties = new Properties();
            inputStream = MemcachedUtil.class.getClassLoader().getResourceAsStream("memcached.properties");
            properties.load(inputStream);
            //数据缓存服务器，“,”表示配置多个memcached服务
            String[] servers = properties.getProperty("cache.server").replaceAll(" ", "").split(",");
            SockIOPool pool = SockIOPool.getInstance("dataServer");
            pool.setServers(servers);
            pool.setFailover(true);
            pool.setInitConn(10);
            pool.setMinConn(5);
            pool.setMaxConn(50);
            pool.setMaintSleep(30);
            pool.setNagle(false);
            pool.setSocketTO(3000);
            pool.setBufferSize(1024*1024*5);
            pool.setAliveCheck(true);
            pool.initialize(); /* 建立MemcachedClient实例 */
            memCachedClient = new MemCachedClient("dataServer");
        } catch (IOException e) {
            log.error(e.toString());
        }
        catch (Exception ex) {
            log.error(ex.toString());
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    log.error(e.toString());
                }
            }
        }
    }
    @Override
    public boolean add(String key, Object value) {
        if(StringUtils.isEmpty(key)){
            return false;
        }
        return memCachedClient.add(key,value);
    }

    @Override
    public boolean add(String key, Object value, Date expiry) {
        if(StringUtils.isEmpty(key)){
            return false;
        }
        return memCachedClient.add(key,value,expiry);
    }

    @Override
    public boolean delete(String key) {
        if(StringUtils.isEmpty(key)){
            return false;
        }
        return memCachedClient.delete(key);
    }

    @Override
    public Object get(String key) {
        if(StringUtils.isEmpty(key)){
            return null;
        }
        return memCachedClient.get(key);
    }

    @Override
    public boolean replace(String key, Object value) {
        if(StringUtils.isEmpty(key)){
            return false;
        }
        return memCachedClient.replace(key,value);
    }

    @Override
    public boolean replace(String key, Object value, Date expiry) {
        if(StringUtils.isEmpty(key)){
            return false;
        }
        return memCachedClient.replace(key,value,expiry);
    }

    @Override
    public boolean set(String key, Object value) {
        if(StringUtils.isEmpty(key)){
            return false;
        }
        return memCachedClient.set(key,value);
    }

    @Override
    public boolean set(String key, Object value, Date expiry) {
        if(StringUtils.isEmpty(key)){
            return false;
        }
        return memCachedClient.set(key,value,expiry);
    }

    @Override
    public boolean keyExists(String key) {
        if(StringUtils.isEmpty(key)){
            return false;
        }
        return memCachedClient.keyExists(key);
    }

    public static void main(String[] args) {
        //删除缓存
        boolean flag = MemcachedUtil.getInstance().delete("ProdProductSingle_ship_1001986");
        //flag = MemcachedUtil.getInstance().delete("ProdProductSingle_ship_1001985");
        System.out.println(flag);
        
    }
}
