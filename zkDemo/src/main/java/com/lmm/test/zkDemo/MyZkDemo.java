package com.lmm.test.zkDemo;

import java.io.IOException;

/**
 * Created by Administrator on 2016/10/28.
 */
public class MyZkDemo extends ConnectionWatcher{
    public static void main(String[] args) {
        MyZkDemo mzk = new MyZkDemo();
        try {
            mzk.connect("10.113.10.190:2181,10.113.10.191:2182,10.113.10.192:2183");
            int i=1;
            while (i++<10){
                System.out.println(mzk.zk.getSessionId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
