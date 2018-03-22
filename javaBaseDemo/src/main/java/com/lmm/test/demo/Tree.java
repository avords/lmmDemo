package com.lmm.test.demo;

/**
 * Created by Administrator on 2018/3/22.
 */
public class Tree {
    private Object value;
    
    private Tree left;
    
    private Tree right;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Tree getLeft() {
        return left;
    }

    public void setLeft(Tree left) {
        this.left = left;
    }

    public Tree getRight() {
        return right;
    }

    public void setRight(Tree right) {
        this.right = right;
    }
}
