package com.lmm.jdk8.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author: arno.yan
 * @Date: 2020/10/30
 */
public class TestThreadPool {
    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 10000000; i++) {
            testThreadPool();


            ThreadGroup currentGroup =
                    Thread.currentThread().getThreadGroup();
            int noThreads = currentGroup.activeCount();

            System.out.println(noThreads);
            System.out.println("第" + i + "次循环");
        }
    }

    public static void testThreadPool() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        List<TestCallable> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tasks.add(new TestCallable());
        }

        List<Future<Void>> futures = executorService.invokeAll(tasks, 10, TimeUnit.SECONDS);

        for (Future future : futures) {
            future.get();
        }
        executorService.shutdown();
    }

    static class TestCallable implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            int a = 1;
            int b = 2;
            int c = a + b;
            return null;
        }
    }
}
