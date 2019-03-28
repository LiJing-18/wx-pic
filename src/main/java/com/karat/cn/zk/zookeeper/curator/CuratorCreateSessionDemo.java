package com.karat.cn.zk.zookeeper.curator;

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
		CuratorFramework curatorFramework1=CuratorFrameworkFactory
				.newClient(CONNECTSTRING,5000,5000,
						new ExponentialBackoffRetry(1000, 3));//重试机制
		curatorFramework1.start();
		//fluent风格
		CuratorFramework curatorFramework2=CuratorFrameworkFactory
				.builder()
				.connectString(CONNECTSTRING)//连接地址
				.sessionTimeoutMs(5000)//会话超时时间，单位为毫秒，默认60000ms 
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))//重试机制
				.namespace("/curator")//namespace命名空间，在此节点下操作
				.build();
		curatorFramework2.start();
		System.out.println("success");
		
	}
}
