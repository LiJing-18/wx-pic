package com.karat.cn.other.zk.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 会话连接工具类
 * @author Administrator
 *
 */
public class CuratorClientUtils {

    private static CuratorFramework curatorFramework;
    
    private final static String CONNECTSTRING="47.107.121.215:2181";


    public static CuratorFramework getInstance(){
        curatorFramework= CuratorFrameworkFactory
        		.newClient(CONNECTSTRING,5000,5000,
                        new ExponentialBackoffRetry(1000,3));
        curatorFramework.start();
        return curatorFramework;
    }
}
