package com.karat.cn.zk.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * 监听
 * @author Administrator
 *
 */
public class CuratorEventDemo {

    /**
     * 三种watcher来做节点的监听
     * pathcache   监视一个路径下子节点的创建、删除、节点数据更新
     * NodeCache   监视一个节点的创建、更新、删除
     * TreeCache   pathcaceh+nodecache 的合体（监视路径下的创建、更新、删除事件），
     * 缓存路径下的所有子节点的数据
     */
    @SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
        //连接
    	CuratorFramework curatorFramework=CuratorClientUtils.getInstance();
        /**
         * 监视一个节点的创建、更新、删除
         */
        //监听1
        NodeCache cache1=new NodeCache(curatorFramework,"/curator",false);
        cache1.start(true);
        //监听事件
        cache1.getListenable().addListener(()-> 
        				System.out.println("节点数据发生变化,变化后的结果" 
        				+"："+new String(cache1.getCurrentData().getData())));
        //修改节点
        curatorFramework.setData().forPath("/curator","666".getBytes());

        /**
         * PatchChildrenCache监听数据节点的增删改，会触发事件
         */
    	//监听2
    	//子节点添加watcher
        PathChildrenCache cache2=new PathChildrenCache(curatorFramework,"/event",true);//参数2监听的节点，参数3是否缓存
        cache2.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        // Normal(初始时为空)
        // BUILD_INITIAL_CACHE(在这个方法返回之前调用rebuild()) 
        // POST_INITIALIZED_EVENT(当Cache初始化数据后发送一个PathChildrenCacheEvent.Type#INITIALIZED事件)
        cache2.getListenable().addListener((curatorFramework1,pathChildrenCacheEvent)->{
            switch (pathChildrenCacheEvent.getType()){
                case CHILD_ADDED:
                    System.out.println("增加子节点");
                    break;
                case CHILD_REMOVED:
                    System.out.println("删除子节点");
                    break;
                case CHILD_UPDATED:
                    System.out.println("更新子节点");
                    break;
                default:break;
            }
        });
        /*==========================================================================*/
        
        
        //创建持久化节点(PERSISTENT持久的)
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/event","event".getBytes());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("1");
        
        //创建临时子节点(EPHEMERAL临时)
        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/event/event1","1".getBytes());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("2");
        //修改节点
        curatorFramework.setData().forPath("/event/event1","222".getBytes());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("3");
        //删除节点
        curatorFramework.delete().forPath("/event/event1");
        System.out.println("4");
         
        
        /*==========================================================================*/
        
        System.in.read();
    }
}
