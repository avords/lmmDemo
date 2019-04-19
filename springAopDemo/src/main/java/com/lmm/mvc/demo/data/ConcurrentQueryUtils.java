package com.lmm.mvc.demo.data;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: arno.yan
 * @Date: 2019/4/19
 */
@Component
public class ConcurrentQueryUtils<T> {

    private ThreadPoolExecutor executors = new ThreadPoolExecutor(30, 100,
            120, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000));


    public List<T> query(List categoryIds, ConcurrentQueryCallback<T> callback) {
        if (CollectionUtils.isEmpty(categoryIds) || categoryIds.size() == 1) {
            return Arrays.asList(callback.query(categoryIds));
        } else {

            List<T> list = new ArrayList<>();
            List<List> categoryIdsGroup = groupByCount(categoryIds);
            List<Future<T>> futureList = new ArrayList<>();

            for (final List ids : categoryIdsGroup) {
                Future<T> future = executors.submit(() -> callback.query(ids));
                futureList.add(future);
            }

            for (Future<T> future : futureList) {
                try {
                    T partialResult = future.get();
                    list.add(partialResult);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            return list;
        }
    }

    public List groupByCount(List categoryIds) {
        if (CollectionUtils.isEmpty(categoryIds)) {
            return new ArrayList<>();
        }

        List<List> list = new ArrayList<>();

        List temp = null;
        for (int i = 0; i < categoryIds.size(); i++) {
            if (i % 10 == 0) {
                temp = new ArrayList<>();
                list.add(temp);
            }
            temp.add(categoryIds.get(i));
        }

        return list;
    }

    public interface ConcurrentQueryCallback<T> {

        T query(List categoryIds);
    }
}
