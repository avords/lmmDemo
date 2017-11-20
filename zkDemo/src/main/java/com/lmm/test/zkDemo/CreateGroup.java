package com.lmm.test.zkDemo;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2016/10/27.
 */
public class CreateGroup implements Watcher {
    private static final int SESSION_TIMEOUT=5000;

    private ZooKeeper zk;
    private CountDownLatch connectedSignal=new CountDownLatch(1);
    @Override
    public void process(WatchedEvent event) {
        if(event.getState()== Event.KeeperState.SyncConnected){
            connectedSignal.countDown();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        CreateGroup createGroup = new CreateGroup();
        createGroup.connect("127.0.0.1:1281");
        createGroup.create("ydqGroup");
        createGroup.close();
    }

    private void close() throws InterruptedException {
        zk.close();
    }

    private void create(String groupName) throws KeeperException, InterruptedException {
        String path="/"+groupName;
        if(zk.exists(path, false)== null){
            zk.create(path, null/*data*/, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        System.out.println("Created:"+path);
    }

    private void connect(String hosts) throws IOException, InterruptedException {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
        connectedSignal.await();
    }
}
