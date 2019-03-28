package com.karat.cn.zk.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;
/**
 * 创建会话(连接zookeeper)
 * @author 开发
 *
 */
public class SessionDemo {

	 private final static String CONNECTSTRING="47.107.121.215:2181";

	 public static void main(String[] args) {
	      ZkClient zkClient=new ZkClient(CONNECTSTRING,4000);
	      System.out.println(zkClient+" - > success连接");  
	 }
}
