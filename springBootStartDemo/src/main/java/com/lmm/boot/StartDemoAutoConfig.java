package com.lmm.boot;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Administrator on 2017/11/23.
 */
@Configuration
@Import(StartDemoBean.class)
@EnableConfigurationProperties(StartDemoProperties.class)
public class StartDemoAutoConfig {
}
