package com.karat.cn.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 创建会话连接
 * @author Administrator
 *
 */
public class CuratorCreateSessionDemo {
	private final static String CONNECTSTRING="47.107.121.215:2181";
	
	public static void main(String args[]) {
		//创建会话连接的2种方式
		//正常的风格
		CuratorFramework curatorFramework1=CuratorFrameworkFactory.
				newClient(CONNECTSTRING,5000,5000,
						new ExponentialBackoffRetry(1000, 3));//重试机制
		curatorFramework1.start();
		//fluent风格
		CuratorFramework curatorFramework2=CuratorFrameworkFactory
				.builder()
				.connectString(CONNECTSTRING)
				.sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.namespace("/curator").build();//namespace命名空间，在此节点下操作
		curatorFramework2.start();
		System.out.println("success");
		
	}
}
