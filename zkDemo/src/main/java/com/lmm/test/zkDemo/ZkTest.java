package com.lmm.test.zkDemo;

import com.lmm.test.zkDemo.ConcurrentTest.ConcurrentTask;

/**
 * Created by Administrator on 2016/11/1.
 */
public class ZkTest {
    public static void main(String[] args) {
        Runnable task1 = new Runnable(){
            public void run() {
                DistributedLock lock = null;
                try {
                    lock = new DistributedLock("10.113.10.190:2181,10.113.10.191:2182,10.113.10.192:2183","ydqtest");
                    //lock = new DistributedLock("127.0.0.1:2182","test2");
                    lock.lock();
                    Thread.sleep(3000);
                    System.out.println("===Thread " + Thread.currentThread().getId() + " running");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if(lock != null)
                        lock.unlock();
                }

            }

        };
        new Thread(task1).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        ConcurrentTask[] tasks = new ConcurrentTask[60];
        for(int i=0;i<tasks.length;i++){
            ConcurrentTask task3 = new ConcurrentTask(){
                public void run() {
                    DistributedLock lock = null;
                    try {
                        lock = new DistributedLock("10.113.10.190:2181,10.113.10.191:2182,10.113.10.192:2183","ydqtest");
                        lock.lock();
                        System.out.println("Thread " + Thread.currentThread().getId() + " running");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        lock.unlock();
                    }

                }
            };
            tasks[i] = task3;
        }
        new ConcurrentTest(tasks);
    }
}
