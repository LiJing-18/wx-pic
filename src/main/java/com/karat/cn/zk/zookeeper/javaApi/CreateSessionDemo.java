package com.karat.cn.zk.zookeeper.javaApi;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * 创建会话(连接zookeeper)
 * @author 开发
 *
 */
public class CreateSessionDemo {

	private final static String CONNECTSTRING="47.107.121.215:2181";
	
	private static CountDownLatch countDownLatch=new CountDownLatch(1);
	
	public static void main(String []args) throws IOException, InterruptedException{
		ZooKeeper zooKeeper=new ZooKeeper(CONNECTSTRING, 5000,new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				//如果当前的连接状态是连接成功的，那么通过计数器去控制
				if(event.getState()==Event.KeeperState.SyncConnected){
					countDownLatch.countDown();
					System.out.println(event.getState());
				}
			}
		});
		countDownLatch.await();
		System.out.println(zooKeeper.getState());
	}
}
