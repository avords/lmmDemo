package com.lmm.utils;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author: arno.yan
 * @Date: 2019-07-16
 */
public class SyncTest extends AbstractQueuedSynchronizer {
    public SyncTest() {
        super();
    }

    @Override
    protected boolean tryAcquire(int arg) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0 && !hasQueuedPredecessors() && compareAndSetState(c, 1)) {
            setExclusiveOwnerThread(current);
            return true;
        } else if (current == getExclusiveOwnerThread()) {
            int nextc = c + arg;
            if (nextc < 0)
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        return false;
    }

    @Override
    protected boolean tryRelease(int arg) {
        int c = getState() - arg;
        if (Thread.currentThread() != getExclusiveOwnerThread())
            throw new IllegalMonitorStateException();
        boolean free = false;
        if (c == 0) {
            free = true;
            setExclusiveOwnerThread(null);
        }
        setState(c);
        return free;
    }
}
