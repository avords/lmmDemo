package com.lmm.distributed.lock.redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by arno.yan on 2019/1/10.
 */
public class DistributedLock implements Lock {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLock.class);

    public static int WAIT_LOCKERS = 2;//当前服务器等待锁的线程数量，如果超过或等于此值，当前线程直接返回，不再等待锁
    public static String REDIS_KEY = "mantu:dislock:";//redis下key前缀
    public static int LOCK_EXPIRE_TIME = 5;//锁的过期时间，单位秒，默认5秒过期

    private JedisPool jedisPool;

    private String lockKey;
    private AtomicInteger waitToLock = new AtomicInteger(0);
    private transient Thread exclusiveOwnerThread;

    public DistributedLock(String lockKey) {
        this.lockKey = StringUtils.join(REDIS_KEY, lockKey);

        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(300);
        config.setMaxIdle(300);
        config.setMinIdle(300);

        //连接池耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时，会抛出超时异常，默认为true
        config.setBlockWhenExhausted(true);

        //这里超时时间是2s
        jedisPool = new JedisPool(config, "127.0.0.1", 6379, 1000 * 2);
    }

    @Override
    public void lock() {
        throw new RuntimeException("please call the try lock method!");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        this.lock();
    }

    @Override
    public boolean tryLock() {
        Thread thread = Thread.currentThread();
        if (thread == this.getExclusiveOwnerThread()) {
            return true;
        }

        return execute(jedis -> {
            Long i = jedis.setnx(lockKey, String.valueOf(System.currentTimeMillis()));
            if (i.intValue() == 1) {
                jedis.expire(lockKey, LOCK_EXPIRE_TIME);
                setExclusiveOwnerThread(thread);
                return true;
            } else {//对于可能性非常低的死锁情况进行解锁
                String initTime = jedis.get(lockKey);
                if (initTime == null) {
                    LOGGER.debug("initTime's value is null");
                    return false;
                }

                long iniTime;
                try {
                    iniTime = Long.parseLong(initTime);
                } catch (NumberFormatException e) {
                    LOGGER.warn(e.getMessage());
                    jedis.expire(lockKey, 1);
                    return false;
                }

                if (((System.currentTimeMillis() - iniTime) / 1000 - LOCK_EXPIRE_TIME - 1) > 0) {
                    //可能在删除的过程中被其他线程改动了，则事务执行失败
                    jedis.watch(lockKey);
                    Transaction transaction = jedis.multi();
                    transaction.del(lockKey);
                    List<Object> list = transaction.exec();
                    if (list == null) {
                        LOGGER.info("already processed by other threads");
                    }
                }
            }
            return false;
        });
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {

        long nanosTimeout = unit.toNanos(time);
        long lastTime = System.nanoTime();

        if (tryLock()) {
            return true;
        }
        try {
            int waitLockers = waitToLock.getAndIncrement();
            if (waitLockers >= WAIT_LOCKERS) {
                LOGGER.debug("wait the lock' thread num is much,so return flase");
                return false;
            }
            for (; ; ) {
                if (tryLock()) {
                    return true;
                }
                if (nanosTimeout <= 0) {
                    LOGGER.debug("get lock timeout");
                    return false;
                }
                if (nanosTimeout > 100000) {
                    LockSupport.parkNanos(100000);//中断100毫秒
                }
                long now = System.nanoTime();
                nanosTimeout -= now - lastTime;
                lastTime = now;

                if (nanosTimeout <= 0) {
                    LOGGER.debug("get lock timeout");
                    return false;
                }
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
            }
        } finally {
            waitToLock.decrementAndGet();
        }
    }

    @Override
    public void unlock() {
        Thread thread = Thread.currentThread();
        if (thread == this.getExclusiveOwnerThread()) {
            execute(jedis -> {
                LOGGER.debug("unlock the thread {}", thread.getId());
                setExclusiveOwnerThread(null);
                jedis.del(lockKey);
                return true;
            });
        }

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    private void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }

    private <T> T execute(JedisCallback<T> callback) {

        Jedis jedis = jedisPool.getResource();

        try {
            return callback.doInJedis(jedis);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    interface JedisCallback<T> {

        T doInJedis(Jedis jedis);
    }
}
