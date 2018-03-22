package com.lmm.test.demo;

import sun.misc.Unsafe;

/**
 * Created by Administrator on 2018/3/22.
 */
public class UnsafeDemo {
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                    (UnsafeDemo.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile int value;

    public final int getAndSet(int newValue) {
        for (;;) {
            int current = get();
            if (compareAndSet(current, newValue))
                return current;
        }
    }
    public int get(){
        return value;
    }
    public final boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }
    
    public static void main(String[] args) {
        UnsafeDemo unsafeDemo = new UnsafeDemo();
        unsafeDemo.getAndSet(1213);
    }
}
