package com.lmm.jdbuy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/27.
 */
public class PermUserServiceAdapterImpl {
    Map<Long, PermUserCacheBean> puMemcache = Collections.synchronizedMap(new HashMap<Long, PermUserCacheBean>(3000));
    public String getPermUserByUserId(Long userId) {
        PermUserCacheBean permUserCache = puMemcache.get(userId);
        if(permUserCache == null || System.currentTimeMillis()-permUserCache.time>600000) {
            permUserCache = new PermUserCacheBean();
            permUserCache.userName="雷涛";
            permUserCache.time=System.currentTimeMillis();
            puMemcache.put(userId, permUserCache);
        }
        return permUserCache.userName;
    }
    class PermUserCacheBean {
        String userName;
        Long time;
    }

    public static void main(String[] args) {
        PermUserServiceAdapterImpl pu = new PermUserServiceAdapterImpl();
        String userName = pu.getPermUserByUserId(123L);
        System.out.println(userName);
    }
}
