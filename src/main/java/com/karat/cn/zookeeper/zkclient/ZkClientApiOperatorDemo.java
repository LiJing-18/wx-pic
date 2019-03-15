package com.karat.cn.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class ZkClientApiOperatorDemo {
	
	private final static String CONNECTSTRING="47.107.121.215:2181";

    private static ZkClient getInstance(){
        return new ZkClient(CONNECTSTRING,10000);
    }

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient=getInstance();//连接
        
        zkClient.createEphemeral("/zkClient");//创建临时节点
        
        zkClient.createPersistent("/zkClient/a/b/c/d");
        
    }
}
