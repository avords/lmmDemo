package com.kl.quartz;

/**
 * @author: arno.yan
 * @Date: 2020/4/6
 */
public class QuartzKongLong {
    public static void main(String[] args) {
        QuartzManager.addJob("konglongJob", KongLongJob.class, "0 0 15 * * ?");//不支持任务并发，实现StatefulJob接口
        QuartzManager.startJobs();
    }
}
