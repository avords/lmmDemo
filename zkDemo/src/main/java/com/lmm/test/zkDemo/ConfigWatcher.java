package com.lmm.test.zkDemo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.IOException;

/**
 * Created by Administrator on 2016/10/27.
 */
public class ConfigWatcher implements Watcher {
    private ActiveKeyValueStore store;

    @Override
    public void process(WatchedEvent event) {
        if(event.getType()== Event.EventType.NodeDataChanged){
            try{
                dispalyConfig();
            }catch(InterruptedException e){
                System.err.println("Interrupted. exiting. ");
                Thread.currentThread().interrupt();
            }catch(KeeperException e){
                System.out.printf("KeeperException锛?s. Exiting.\n", e);
            }

        }

    }
    public ConfigWatcher(String hosts) throws IOException, InterruptedException {
        store=new ActiveKeyValueStore();
        store.connect(hosts);
    }
    public void dispalyConfig() throws KeeperException, InterruptedException{
        String value=store.read(ConfigUpdater.PATH, this);
        System.out.printf("Read %s as %s\n",ConfigUpdater.PATH,value);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ConfigWatcher configWatcher = new ConfigWatcher("10.113.10.190:2181,10.113.10.191:2182,10.113.10.192:2183");
        configWatcher.dispalyConfig();
        //stay alive until process is killed or Thread is interrupted
        Thread.sleep(Long.MAX_VALUE);
    }
}
