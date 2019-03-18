package com.karat.cn.zkLock.javaapilock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁的实现
 */
public class DistributeLock {


    private static final String ROOT_LOCKS="/LOCKS";//根节点

    private ZooKeeper zooKeeper;//zooKeeper实列

    private int sessionTimeout; //会话超时时间

    private String lockID; //记录锁节点id

    private final static byte[] data={1,2}; //节点的数据

    private CountDownLatch countDownLatch=new CountDownLatch(1);//计数器
    
    //会话连接
    public DistributeLock() throws IOException, InterruptedException {
        this.zooKeeper=ZookeeperClient.getInstance();
        this.sessionTimeout=ZookeeperClient.getSessionTimeout();
    }

    //获取锁的方法
    public boolean lock(){
        try {
        	//创建一个临时有序节点
            lockID=zooKeeper.create(ROOT_LOCKS+"/",data, ZooDefs.Ids.
                    OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName()
            		+"->成功创建了lock节点["+lockID+"], 开始去竞争锁");
            //获取当前根节点下的所有子节点
            List<String> childrenNodes=zooKeeper.getChildren(ROOT_LOCKS,true);//获取根节点下的所有子节点
            //排序，从小到大（树节点）
            SortedSet<String> sortedSet=new TreeSet<String>();
            for(String children:childrenNodes){
                sortedSet.add(ROOT_LOCKS+"/"+children);
            }
            String first=sortedSet.first(); //拿到最小的节点
            if(lockID.equals(first)){//如果刚创建的临时节点就是最小节点，那么就没有其它子节点，当前新建节点获取锁成功
                //表示当前就是最小的节点
                System.out.println(Thread.currentThread().getName()+"->成功获得锁，lock节点为:["+lockID+"]");
                return true;
            }
            //当当前创建的临时节点不是最小节点时，说明之前已有创建的临时节点，之前临时节点正在使用锁，等待锁释放
            SortedSet<String> lessThanLockId=sortedSet.headSet(lockID);
            if(!lessThanLockId.isEmpty()){
                String prevLockID=lessThanLockId.last();//拿到比当前LOCKID这个几点更小的上一个节点
                zooKeeper.exists(prevLockID,new LockWatcher(countDownLatch));//监控是否有删除节点的操作(释放锁)
                countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);//等待锁释放(会话超时时间)(挂起线程)
                //上面这段代码意味着如果会话超时或者节点被删除（释放）了
                System.out.println(Thread.currentThread().getName()+" 成功获取锁：["+lockID+"]");
            }
            return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    //释放锁
    public boolean unlock(){
        System.out.println(Thread.currentThread().getName()
        		+"->开始释放锁:["+lockID+"]");
        try {
            zooKeeper.delete(lockID,-1);//删除当前节点(释放锁)
            System.out.println("节点["+lockID+"]成功被删除");
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        final CountDownLatch latch=new CountDownLatch(10);
        Random random=new Random();
        for(int i=0;i<10;i++){
            new Thread(()->{
                DistributeLock lock=null;
                try {
                    lock=new DistributeLock();//会话连接
                    latch.countDown();//减一
                    latch.await();//等待
                    lock.lock();//获取锁
                    Thread.sleep(random.nextInt(500));//睡眠
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if(lock!=null){
                        lock.unlock();//释放锁
                    }
                }
            }).start();//启动线程
        }
    }
}
