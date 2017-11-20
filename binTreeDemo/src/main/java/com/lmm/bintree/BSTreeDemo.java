package com.lmm.bintree;

/**
 * Created by Administrator on 2017/7/28.
 */
public class BSTreeDemo {
    public static void main(String[] args) {//排序二叉树并不是唯一的
        BSTree<Integer> bsTree = new BSTree<>();
        bsTree.insert(96);
        bsTree.insert(23);
        bsTree.insert(9);
        bsTree.insert(98);
        bsTree.insert(56);
        bsTree.insert(63);
        bsTree.insert(22);
        bsTree.insert(77);
        bsTree.insert(54);
        
        bsTree.print();
        bsTree.inOrder();
        System.out.println("\n============================================================================");
        BSTree<Integer> bsTree1 = new BSTree<>();
        bsTree1.insert(23);
        bsTree1.insert(96);
        bsTree1.insert(9);
        bsTree1.insert(98);
        bsTree1.insert(56);
        bsTree1.insert(63);
        bsTree1.insert(22);
        bsTree1.insert(77);
        bsTree1.insert(54);

        bsTree1.print();
        bsTree1.inOrder();
    }
}
