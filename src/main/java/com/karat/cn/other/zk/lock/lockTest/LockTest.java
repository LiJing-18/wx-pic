package com.karat.cn.other.zk.lock.lockTest;
import org.apache.zookeeper.*;

import com.google.common.base.Strings;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
/**
 * 分布式锁
 * 大量锁的情况下会有“惊群”的问题
 * @author 开发
 *
 */
public class LockTest{
 
	private String zkQurom = "47.107.121.215:2181";

    private String lockNameSpace = "/LOCKS";

    private String nodeString = lockNameSpace + "/test1";

    private ZooKeeper zk;
    //连接
    public LockTest(){
        try {
            zk = new ZooKeeper(zkQurom, 6000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("当前监听是"+watchedEvent);
                    if(Event.KeeperState.SyncConnected == watchedEvent.getState())
                        System.out.println("成功连接");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //创建连接
    private void ensureRootPath() throws InterruptedException {
        try {
            if (zk.exists(lockNameSpace,true)==null){
                zk.create(lockNameSpace,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
    //监听
    private void watchNode(String nodeString, final Thread thread) throws InterruptedException {
        try {
            zk.exists(nodeString, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println( "==" + watchedEvent.toString());
                    if(watchedEvent.getType() == Event.EventType.NodeDeleted){
                        System.out.println("Threre is a Thread released Lock==============");
                        thread.interrupt();//结束进程
                    }
                    try {
                        zk.exists(nodeString,new Watcher() {
                            @Override
                            public void process(WatchedEvent watchedEvent) {
                                System.out.println( "==" + watchedEvent.toString());
                                if(watchedEvent.getType() == Event.EventType.NodeDeleted){
                                    System.out.println("Threre is a Thread released Lock==============");
                                    thread.interrupt();
                                }
                                try {
                                    zk.exists(nodeString,true);
                                } catch (KeeperException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        });
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取锁
     * @return
     * @throws InterruptedException
     */
    public boolean lock() throws InterruptedException {
        String path = null;
        ensureRootPath();
        watchNode(nodeString,Thread.currentThread());
        while (true) {
            try {
                path = zk.create(nodeString, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            } catch (KeeperException e) {
                System.out.println(Thread.currentThread().getName() + "  getting Lock but can not get");
                try {
                    Thread.sleep(5000);
                }catch (InterruptedException ex){
                    System.out.println("thread is notify");
                }
            }
            if (!Strings.nullToEmpty(path).trim().isEmpty()) {
                System.out.println(Thread.currentThread().getName() + "得到锁");
                return true;
            }
        }
    }

    /**
     * 释放锁
     */
    public void unlock(){
        try {
            zk.delete(nodeString,-1);
            System.out.println("释放锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0;i<10;i++){
            service.execute(()-> {
                LockTest test = new LockTest();
                try {
                    test.lock();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.unlock();
            });
        }
        service.shutdown();
    }
}