package com.karat.cn.other.zk.zookeeper.javaApi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 权限控制
 * @author 开发
 *
 */
public class ApiOperatorDemo implements Watcher{
	
	private final static String CONNECTSTRING="47.107.121.215:2181";
	//计数器
	private static CountDownLatch countDownLatch=new CountDownLatch(1);
	
	private static ZooKeeper zookeeper;
	
	private static Stat stat=new Stat();
	
	public static void main(String []args) throws IOException, InterruptedException, KeeperException{
		zookeeper=new ZooKeeper(CONNECTSTRING, 5000,new ApiOperatorDemo());
		countDownLatch.await();
		System.out.println("连接成功");
		//指定授权类型
		ACL acl1=new ACL(ZooDefs.Perms.ALL,new Id("digest","root"));
		ACL acl2=new ACL(ZooDefs.Perms.READ,new Id("ip","47.107.121.215"));
	    List<ACL> acls=new ArrayList<>();
	    acls.add(acl1);
	    acls.add(acl2);
		
		//创建节点
		zookeeper.create("/auth","12333".getBytes(),acls, CreateMode.PERSISTENT);
		//创建子节点
		zookeeper.create("/auth/data", "456".getBytes(),acls, CreateMode.PERSISTENT);
		
		//第二个客户端创建
		ZooKeeper keeper=new ZooKeeper(CONNECTSTRING, 5000,new ApiOperatorDemo());
		//修改
		keeper.setData("/auth/data", "第二个客户端修改".getBytes(), -1);
		
    }

	
	//监控事件
    public void process(WatchedEvent watchedEvent) {
        //如果当前的连接状态是连接成功的，那么通过计数器去控制
        if(watchedEvent.getState()==Event.KeeperState.SyncConnected){
            if(Event.EventType.None==watchedEvent.getType()&&null==watchedEvent.getPath()){
                countDownLatch.countDown();
                System.out.println(watchedEvent.getState()+"-->"+watchedEvent.getType());
            }else if(watchedEvent.getType()== Event.EventType.NodeDataChanged){
                try {
                    System.out.println("数据变更触发路径："+watchedEvent.getPath()+"->改变后的值："+
                            zookeeper.getData(watchedEvent.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(watchedEvent.getType()== Event.EventType.NodeChildrenChanged){//子节点的数据变化会触发
                try {
                    System.out.println("子节点数据变更路径："+watchedEvent.getPath()+"->节点的值："+
                            zookeeper.getData(watchedEvent.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(watchedEvent.getType()== Event.EventType.NodeCreated){//创建子节点的时候会触发
                try {
                    System.out.println("节点创建路径："+watchedEvent.getPath()+"->节点的值："+
                            zookeeper.getData(watchedEvent.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(watchedEvent.getType()== Event.EventType.NodeDeleted){//子节点删除会触发
                System.out.println("节点删除路径："+watchedEvent.getPath());
            }
            System.out.println(watchedEvent.getType());
        }

    }
}
