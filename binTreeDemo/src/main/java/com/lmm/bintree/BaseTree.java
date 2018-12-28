package com.lmm.bintree;

/**
 * Created by arno.yan on 2018/12/25.
 */
public interface BaseTree {
    public static final long ROOT = -1;
    public static final int FOLDER = 1;
    public static final int FILE = 2;
    /**
     * @return parent ID
     */
    public Long getParentId();
    /**
     * @return Object ID
     */
    public Long getObjectId();
}
