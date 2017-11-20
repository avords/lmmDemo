package com.lmm.dbDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2016/12/30.
 */
public class ThreadLocalDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<String>> resultList = new ArrayList<Future<String>>();
        CountDownLatch end = new CountDownLatch(100);
        for(int i=1;i<101;i++){
            Future<String> future =  executorService.submit(new ThreadDemo(i,end));
            resultList.add(future);
        }
        executorService.shutdown();
        end.await();
        //遍历任务的结果
        for (Future<String> fs : resultList) {
            try {
                System.out.println(fs.get());     //打印各个线程（任务）执行的结果
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
                executorService.shutdown();
            }
        }
    }
}
