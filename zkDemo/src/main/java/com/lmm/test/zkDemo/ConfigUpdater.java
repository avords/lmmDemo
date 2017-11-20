package com.lmm.test.zkDemo;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/10/27.
 */
public class ConfigUpdater {

    public static final String  PATH="/ydqconfig";

    private ActiveKeyValueStore store;
    private Random random=new Random();

    public ConfigUpdater(String hosts) throws IOException, InterruptedException {
        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }
    public void run() throws InterruptedException, KeeperException {
        while(true){
            String value=random.nextInt(100)+"";
            store.write(PATH, value);
            System.out.printf("Set %s to %s\n",PATH,value);
            TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));

        }
    }
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ConfigUpdater configUpdater = new ConfigUpdater("10.113.10.190:2181,10.113.10.191:2182,10.113.10.192:2183");
        configUpdater.run();
    }
}
