package com.lmm.ignite;

import org.apache.commons.lang3.StringUtils;
import org.apache.ignite.IgniteState;
import org.apache.ignite.Ignition;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by arno.yan on 2018/12/29.
 */
public class IgniteCacheStarter implements ApplicationListener<ContextRefreshedEvent>, InitializingBean {
    private String configFile = "config/ignite/ignite-cache.xml";
    private IgniteCacheLoader cacheLoader;
    private String gridName;

    public IgniteCacheStarter() {
    }

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            this.start();
        }

    }

    private void start() {
        if (this.cacheLoader != null) {
            this.cacheLoader.load(Ignition.ignite(this.gridName));
        }

    }

    public void setCacheLoader(IgniteCacheLoader cacheLoader) {
        this.cacheLoader = cacheLoader;
    }

    public void afterPropertiesSet() throws Exception {
        if (!Ignition.state(this.gridName).equals(IgniteState.STARTED)) {
            if (StringUtils.isNotEmpty(this.configFile)) {
                Ignition.start(this.configFile);
            } else {
                Ignition.start();
            }
        }

    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }
}
