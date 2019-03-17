package com.karat.cn.zookeeper.zkclient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
/**
 * 节点操作
 * @author Administrator
 *
 */
public class ZkClientApiOperatorDemo {
	
	private final static String CONNECTSTRING="47.107.121.215:2181";

    private static ZkClient getInstance(){
        return new ZkClient(CONNECTSTRING,10000);//10000是连接超时时间
    }

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient=getInstance();//连接
        //zkClient.createEphemeral("/zkClient");//创建临时节点
        //zkClient中提供递归创建父节点的功能
        //zkClient.createPersistent("/zkClient/a/b/c/d",true);//创建持久化节点
        //删除节点
        //zkClient.delete("/zkClient");
        //递归删除节点
        //zkClient.deleteRecursive("/zkClient");
        //获取子节点
        List<String> list=zkClient.getChildren("/zkClient/a");
        System.out.println(list);
        
        
        //watcher(订阅获取)
        zkClient.subscribeDataChanges("/node", new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("节点名称："+dataPath+"->节点修改后的值"+data);
			}
		});
        
        zkClient.writeData("/node", "123");//修改节点的数据 
        TimeUnit.SECONDS.sleep(2);//上述事件是异步的，需要睡两秒
        
        zkClient.subscribeChildChanges("/node", new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
    }
}
