package com.karat.cn.zk.master.zkCuratorMaster.leaderSelector;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;
/**
 * LeaderSelector选举
 * 每一个应用都写一个临时有序节点，根据最小的节点来获得优先权
 * @author 开发
 *
 */
public class MasterSelector {

	private final static String CONNECTSTRING="47.107.121.215:2181";//地址

    private final static String MASTER_PATH="/curator_master_path";//根节点
    
    
    @SuppressWarnings("resource")
	public static void main(String[] args) {
    	//创建会话连接
        CuratorFramework curatorFramework= CuratorFrameworkFactory
        		.builder()
        		.connectString(CONNECTSTRING)
        		.retryPolicy(new ExponentialBackoffRetry(1000,3))
        		.build();
        //启动进程，启动成功回调下面方法
        LeaderSelector leaderSelector=new LeaderSelector(curatorFramework, MASTER_PATH, new LeaderSelectorListenerAdapter() {
            @Override//能够进入重写的takeLeadership方法的表示获取锁成功，出去就代表释放锁
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("获得leader成功");
                TimeUnit.SECONDS.sleep(2);
            }
        });
        
        leaderSelector.autoRequeue();
        leaderSelector.start();//开始选举
    }
}
