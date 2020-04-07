package com.kl.quartz;

import com.kl.KongLongClient;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: arno.yan
 * @Date: 2020/4/6
 */
public class KongLongJob implements StatefulJob {

    private static final Logger logger = LoggerFactory.getLogger(KongLongJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            KongLongClient.start();
        } catch (Exception e) {
            logger.info("==========KongLongJob发生异常============");
            throw new JobExecutionException("ftustd 方法体执行异常");
        }
    }
}
