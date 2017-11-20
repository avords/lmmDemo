package com.lmm.thread;

/**
 * Created by Administrator on 2017/5/25.
 */
public class ThreadMaxNumDemo {
    public static int i= 0;
    private static Object a = new Object();
    public static void main(String[] args) {
        for (int index=0;index<10000;index++){
            Thread t = new Thread(){
                @Override
                public void run() {
                    iPlus();
                    System.out.println(i);
                }
            };
            t.start();
        }
    }
    public static int iPlus(){
        synchronized (a){
            return i++;
        }
    }
}