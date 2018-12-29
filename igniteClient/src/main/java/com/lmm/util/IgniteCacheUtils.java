package com.lmm.util;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by frank.li on 2017/10/13.
 */
@Component
public class IgniteCacheUtils {

    @Value("#{ignitecache['ignite.grid.name']}")
    private String gridName;

    public Ignite getIgnite() {
        return Ignition.ignite(gridName);
    }

    public <K, V> IgniteCache<K, V> getIgniteCache(String cacheName) {
        IgniteCache<K, V> cache = getIgnite().cache(cacheName);
        return cache;
    }

    public IgniteAtomicSequence getAtomicSequence(String name) {
        Ignite ignite = getIgnite();
        Date currentDate = new Date();
        String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(currentDate);
        Long initVal = Long.parseLong(dateString + "00000");
        return ignite.atomicSequence(name, initVal, true);
    }
}
