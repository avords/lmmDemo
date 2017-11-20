package com.lmm.test.zkDemo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2016/10/27.
 */
public class ConnectionWatcher implements Watcher{
    private static final int SESSION_TIMEOUT=50000;

    protected ZooKeeper zk;
    CountDownLatch connectedSignal=new CountDownLatch(1);
    public void connect(String host) throws IOException, InterruptedException{
        try {
            zk = new ZooKeeper(host, SESSION_TIMEOUT, this);
            connectedSignal.await();
        }catch ( InterruptedException e ) {
            System.out.println( "连接创建失败，发生 InterruptedException" );
            e.printStackTrace();
        } catch ( IOException e ) {
            System.out.println( "连接创建失败，发生 IOException" );
            e.printStackTrace();
        }catch ( Exception e ) {
            System.out.println( "未知异常" );
            e.printStackTrace();
        }
    }
    @Override
    public void process(WatchedEvent event) {
        if(event.getState()== Watcher.Event.KeeperState.SyncConnected){
            connectedSignal.countDown();
        }
    }
    public void close() throws InterruptedException{
        zk.close();
    }
}
