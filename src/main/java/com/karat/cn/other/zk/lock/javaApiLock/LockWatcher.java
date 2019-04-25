package com.karat.cn.other.zk.lock.javaApiLock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 *监控
 */
public class LockWatcher implements Watcher{

    private CountDownLatch latch;

    public LockWatcher(CountDownLatch latch) {
        this.latch = latch;
    }

    public void process(WatchedEvent event) {
        if(event.getType()== Event.EventType.NodeDeleted){//监听到当前节点是否删除
            latch.countDown();//唤醒休眠线程
        }
    }
}
