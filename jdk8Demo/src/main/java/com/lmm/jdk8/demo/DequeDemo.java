package com.lmm.jdk8.demo;

import java.util.Deque;
import java.util.LinkedList;

public class DequeDemo {
    public static void main(String[] args) {
        Deque deque = new LinkedList();
        deque.push(1);
        deque.push(2);
        deque.push(3);
        System.out.println(deque.peek());
        System.out.println(deque);
        System.out.println(deque.pollLast());
        System.out.println(deque.peek());
    }
}
