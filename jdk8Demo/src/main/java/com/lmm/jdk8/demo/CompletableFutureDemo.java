package com.lmm.jdk8.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author: arno.yan
 * @Date: 2020/4/21
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {
        CompletableFuture.supplyAsync(()->{System.out.println("我先执行");return 1;})
                .thenAccept(t->{
                    System.out.println("我后执行，上一个流程的参数："+t);
                });
        
        method();
    }

    public static void method() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "f1";
        });

        f1.whenCompleteAsync((s, throwable) -> System.out.println(System.currentTimeMillis() + ":" + s));

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "f2";
        });

        f2.whenCompleteAsync((s, throwable) -> System.out.println(System.currentTimeMillis() + ":" + s));

        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2);

        //阻塞，直到所有任务结束。
        System.out.println(System.currentTimeMillis() + ":阻塞");
        all.join();
        System.out.println(System.currentTimeMillis() + ":阻塞结束");

        //一个需要耗时2秒，一个需要耗时3秒，只有当最长的耗时3秒的完成后，才会结束。
        System.out.println("任务均已完成。");
    }
}
