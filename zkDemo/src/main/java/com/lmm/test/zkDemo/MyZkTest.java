package com.lmm.test.zkDemo;

/**
 * Created by Administrator on 2016/11/4.
 */
public class MyZkTest {
    public static void main(String[] args) {
        for (int i=0;i<200;i++){
            final int j = i;
            Runnable task1 = new Runnable(){
                @Override
                public void run() {
                    DistributedLock dt = new DistributedLock("10.113.10.190:2181,10.113.10.191:2182,10.113.10.192:2183","myzktest");
                    dt.lock();
                    System.out.println("my "+j+" doing");
                    /*try {
                       // Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    dt.unlock();
                }
            };
            Thread t = new Thread(task1);
            t.start();
        }
    }
}
