package com.lmm.bintree;

/**
 * Created by Administrator on 2017/7/28.
 */
public class Node {
    Node leftChild;
    Node rightChild;
    int data;

    Node(int newData) {
        leftChild = null;
        rightChild = null;
        data = newData;
    }
}
