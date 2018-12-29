package com.lmm.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arno.yan on 2018/12/29.
 */
public class InMemoryCacheLoader implements IgniteCacheLoader {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryCacheLoader.class);

    @Override
    public void load(Ignite ignite) {

        createCache(ignite, "test_user", Long.class, User.class);
    }

    private <I, T> void createCache(Ignite ignite, String cacheName, Class idClass, Class<T> entityClass) {

        IgniteCache cache = ignite.cache(cacheName);
        if (cache != null) {
            logger.info("Ignite cache: {} already created", cacheName);
            return;
        }

        logger.info("create ignite cache: {}", cacheName);
        CacheConfiguration<I, T> cacheCfg = new CacheConfiguration<I, T>(cacheName);
        cacheCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        cacheCfg.setIndexedTypes(idClass, entityClass);
        cacheCfg.setStartSize(1000);
        cacheCfg.setCacheMode(CacheMode.REPLICATED);
        cacheCfg.setBackups(0);
        //cacheCfg.setOffHeapMaxMemory(0);
        cacheCfg.setSwapEnabled(false);

        //cacheCfg.setCacheStoreFactory(FactoryBuilder.factoryOf(cacheStoreClass));

        cacheCfg.setReadThrough(false);
        cacheCfg.setWriteThrough(false);

        ignite.getOrCreateCache(cacheCfg);
    }
}
