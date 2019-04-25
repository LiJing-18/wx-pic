package com.karat.cn.other.zk.zookeeper.javaApi;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * java的api对节点的操作
 * @author 开发
 *
 */
public class CreateDodeDemo implements Watcher{

	private final static String CONNECTSTRING="47.107.121.215:2181";
	
	private static CountDownLatch countDownLatch=new CountDownLatch(1);
	
	private static ZooKeeper zooKeeper;
	
	private static Stat stat=new Stat();
	
	public static void main(String []args) throws IOException, InterruptedException, KeeperException{
		/*zooKeeper=new ZooKeeper(CONNECTSTRING, 5000,new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				//如果当前的连接状态是连接成功的，那么通过计数器去控制
				if(event.getState()==Event.KeeperState.SyncConnected){
					if(Event.EventType.None==event.getType()&&null==event.getPath()){
						countDownLatch.countDown();
						System.out.println(event.getState()+"->"+event.getType());	
					}else if(event.getType()== Event.EventType.NodeDataChanged){//数据变更触发路径
		                try {
		                    System.out.println("数据变更触发路径："+event.getPath()+"->改变后的值："+
		                    		zooKeeper.getData(event.getPath(),true,stat));
		                } catch (KeeperException e) {
		                    e.printStackTrace();
		                } catch (InterruptedException e) {
		                    e.printStackTrace();
		                }
		            }else if(event.getType()== Event.EventType.NodeChildrenChanged){//子节点的数据变化会触发
		                try {
		                    System.out.println("子节点数据变更路径："+event.getPath()+"->节点的值："+
		                    		zooKeeper.getData(event.getPath(),true,stat));
		                } catch (KeeperException e) {
		                    e.printStackTrace();
		                } catch (InterruptedException e) {
		                    e.printStackTrace();
		                }
		            }else if(event.getType()== Event.EventType.NodeCreated){//创建子节点的时候会触发
		                try {
		                    System.out.println("节点创建路径："+event.getPath()+"->节点的值："+
		                    		zooKeeper.getData(event.getPath(),true,stat));
		                } catch (KeeperException e) {
		                    e.printStackTrace();
		                } catch (InterruptedException e) {
		                    e.printStackTrace();
		                }
		            }else if(event.getType()== Event.EventType.NodeDeleted){//子节点删除会触发
		                System.out.println("节点删除路径："+event.getPath());
		            }
		            System.out.println(event.getType());
				}
			}
		});*/
		zooKeeper=new ZooKeeper(CONNECTSTRING, 5000,new CreateDodeDemo());//上面代码改成这一行代码
		
		
		countDownLatch.await();
		System.out.println(zooKeeper.getState());
		
		//连接成功创建节点
		String result=zooKeeper.create("/app","app节点创建".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		//注册
		zooKeeper.getData("/app", true, stat);
		System.out.println("创建成功->"+result);
		//修改数据
		zooKeeper.setData("/app", "修改第一次".getBytes(),-1);
		//睡眠2秒
		Thread.sleep(2000);
		//修改数据
		zooKeeper.setData("/app", "修改第二次".getBytes(),-1);
		//睡眠2秒
		Thread.sleep(2000);
		//删除
		//zooKeeper.delete("/app", -1);
		//睡眠2秒
		//Thread.sleep(2000);
		//创建节点和子节点(临时节点下不能挂子节点)
		String path="/node";
		
		zooKeeper.create(path, "节点node".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		TimeUnit.SECONDS.sleep(1);
		
		Stat stat=zooKeeper.exists(path+"/app",true);
		if(stat==null){//表示节点不存在
			zooKeeper.create(path+"/app","node子节点".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            TimeUnit.SECONDS.sleep(1);
        }
		//修改子路径
		zooKeeper.setData(path+"/app","修改后的".getBytes(),-1);
		TimeUnit.SECONDS.sleep(1);
		
		//获取指定节点下的子节点
		List<String> list=zooKeeper.getChildren("/node", true);//第二个参数表示获取是否监控
		System.out.println(list);
		
		
	}
	
	
	/**
	 * 实现接口Watcher中的process抽象方法
	 */
	public void process(WatchedEvent watchedEvent) {
        //如果当前的连接状态是连接成功的，那么通过计数器去控制
        if(watchedEvent.getState()==Event.KeeperState.SyncConnected){
            if(Event.EventType.None==watchedEvent.getType()&&null==watchedEvent.getPath()){
                countDownLatch.countDown();
                System.out.println(watchedEvent.getState()+"-->"+watchedEvent.getType());
            }else if(watchedEvent.getType()== Event.EventType.NodeDataChanged){
                try {
                    System.out.println("数据变更触发路径："+watchedEvent.getPath()+"->改变后的值："+
                    		zooKeeper.getData(watchedEvent.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(watchedEvent.getType()== Event.EventType.NodeChildrenChanged){//子节点的数据变化会触发
                try {
                    System.out.println("子节点数据变更路径："+watchedEvent.getPath()+"->节点的值："+
                    		zooKeeper.getData(watchedEvent.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(watchedEvent.getType()== Event.EventType.NodeCreated){//创建子节点的时候会触发
                try {
                    System.out.println("节点创建路径："+watchedEvent.getPath()+"->节点的值："+
                    		zooKeeper.getData(watchedEvent.getPath(),true,stat));
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
