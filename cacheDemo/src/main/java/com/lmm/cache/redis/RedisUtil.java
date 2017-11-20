package com.lmm.cache.redis;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/12/2.
 */
public class RedisUtil{
    private static Object LOCK = new Object();
    private Properties properties;
    private JedisCluster jedisCluster;
    private static RedisUtil instance;
    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

    private RedisUtil(){
        init();
    }
    private void init(){
        InputStream inputStream = null;
        try {
            properties = new Properties();
            inputStream = RedisUtil.class.getClassLoader().getResourceAsStream("redis.properties");
            properties.load(inputStream);
            String[] servers = properties.getProperty("cache.server").replaceAll(" ", "").split(",");
            Set<HostAndPort> hosts = new HashSet<HostAndPort>();
            for(String server:servers){
                String[] hostAndPort = server.split(":");
                String host = hostAndPort[0];
                String port = hostAndPort[1];
                HostAndPort hp = new HostAndPort(host,Integer.valueOf(port));
                hosts.add(hp);
            }
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(30);
            poolConfig.setMaxIdle(15);
            poolConfig.setMinIdle(10);
            poolConfig.setMaxWaitMillis(1000);

            poolConfig.setBlockWhenExhausted(true);
            jedisCluster = new JedisCluster(hosts, 2000, 5, poolConfig);
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
    /**
     * 改用嵌套类静态实始化
     * @return
     */
    public static RedisUtil getInstance() {
        if(instance == null){
            synchronized(LOCK) {
                if (instance==null) {
                    instance=new RedisUtil();
                }
            }
        }
        return instance;
    }
    
    public boolean delete(String key) {
        Long l = jedisCluster.del(key.getBytes());
        if(l==1){
            return true;
        }
        return false;
    }
    
    public Object get(String key) {
        byte[] bytes = jedisCluster.get(key.getBytes());
        Object obj = SerializationUtils.deserialize(bytes);
        return obj;
    }
    
    public boolean set(String key, Object value) {
        String str = jedisCluster.set(key.getBytes(),SerializationUtils.serialize((Serializable) value));
        if("OK".equals(str)){
            return true;
        }
        return false;
    }
    
    public boolean set(String key, Object value, Date expiry) {
        String str = jedisCluster.setex(key.getBytes(), (int) TimeUnit.MILLISECONDS.toSeconds(expiry.getTime()-new Date().getTime()),SerializationUtils.serialize((Serializable) value));
        if("OK".equals(str)){
            return true;
        }
        return false;
    }
    
    public boolean keyExists(String key) {
        return jedisCluster.exists(key);
    }
}
