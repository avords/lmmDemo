package com.lmm.test.quartzDemo;

/**
 * Created by Administrator on 2016/11/20.
 */
public class QuartzTest {
    public static void main(String[] args) {
        //QuartzManager.addJob("job1", QuartzJob.class, "0/5 * * * * ?"); //支持任务并发，实现Job接口
        //QuartzManager.addJob("job2", QuartzJob.class, "0/5 * * * * ?");
        QuartzManager.addJob("job1", QuartzJobStateful.class, "0/5 * * * * ?");//不支持任务并发，实现StatefulJob接口
        QuartzManager.startJobs();
    }
}
