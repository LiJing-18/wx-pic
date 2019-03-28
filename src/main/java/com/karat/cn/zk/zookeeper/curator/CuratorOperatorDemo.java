package com.karat.cn.zk.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
/*import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;*/
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
/*import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
*/
import java.util.Collection;
/*import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;*/

/**
 * curator对节点的增删改查
 * @author Administrator
 *
 */
public class CuratorOperatorDemo {

    public static void main(String[] args) throws InterruptedException {
        CuratorFramework curatorFramework=CuratorClientUtils.getInstance();
        System.out.println("连接成功.........");

        //fluent风格api增删改查操作
        /**
         * 创建节点
         */
       /*try {
            String result=curatorFramework.create()
            		.creatingParentsIfNeeded()//创建父节点
            		.withMode(CreateMode.PERSISTENT)//持久节点：节点创建后，会一直存在，不会因客户端会话失效而删除；
            		.forPath("/curator/curator1/curator11","123".getBytes());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /**
         * 删除节点
         */
        /*try {
            //默认情况下，version为-1
            curatorFramework.delete()//删除操作
            .deletingChildrenIfNeeded()//删除子节点
            .forPath("/node");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /**
         * 查询
         */
        /*Stat stat=new Stat();
        try {
            byte[] bytes=curatorFramework
            		.getData()
            		.storingStatIn(stat)
            		.forPath("/curator/curator1/curator11");
            System.out.println(new String(bytes)+"-->stat:"+stat);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /**
         * 更新
         */
        /*try {
            Stat stat=curatorFramework
            		.setData()
            		.forPath("/curator","lijing".getBytes());
            System.out.println(stat);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /**
         * 异步操作
         */
        /*ExecutorService service= Executors.newFixedThreadPool(1);//线程池(创建节点的事件由线程池处理)
        CountDownLatch countDownLatch=new CountDownLatch(1);//计数器
        try {
            curatorFramework
            .create()
            .creatingParentsIfNeeded()
            .withMode(CreateMode.EPHEMERAL)
            .inBackground(new BackgroundCallback() {
                 @Override
                 public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                     System.out.println(Thread.currentThread().getName()
                    		 +"->resultCode:"
                    		 +curatorEvent.getResultCode()+"->"//响应结果
                    		 +curatorEvent.getType());//当前节点操作类型
                     countDownLatch.countDown();
                 }
            },service)
            .forPath("/mic","123".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        countDownLatch.await();//等待(让当前线程等待)
        service.shutdown();//关闭线程*/
        /**
         * 事务操作（curator独有的）
         */
        try {
            Collection<CuratorTransactionResult> resultCollections=curatorFramework
            		.inTransaction()//开启一个事务
            		.create()
            		.forPath("/trans","111".getBytes())//创建一个节点
            		.and()//通过and去修改一个节点
            		.setData()
            		.forPath("/curator","111".getBytes())//当修改节点不存在，则一成功一失败，事务不会提交成功
            		.and()
            		.commit();//提交事务
            for (CuratorTransactionResult result:resultCollections){
                System.out.println(result.getForPath()+"->"+result.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
