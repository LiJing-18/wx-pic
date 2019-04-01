package com.karat.cn.zk.lock.javaApiLock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 创建会话
 */
public class ZookeeperClient {

	private final static String CONNECTSTRING="47.107.121.215:2181";

    private static int sessionTimeout=5000;

    //获取连接
    public static ZooKeeper getInstance() throws IOException, InterruptedException {
        
    	final CountDownLatch conectStatus=new CountDownLatch(1);
        
        ZooKeeper zooKeeper=new ZooKeeper(CONNECTSTRING, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {
                if(event.getState()== Event.KeeperState.SyncConnected){//连接成功状态
                    conectStatus.countDown();//唤醒等待的线程
                }
            }
        });
        conectStatus.await();//当前线程休眠等待
        return zooKeeper;
    }

    public static int getSessionTimeout() {
        return sessionTimeout;
    }
}
