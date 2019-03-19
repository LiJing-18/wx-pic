package com.karat.cn.zkLock.lockDemo;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class Test {
	
	private static void sell(){
        System.out.println("售票开始");
        //线程随机休眠数毫秒，模拟现实中的费时操作
        int sleepMillis = (int) (Math.random() * 2000);
        try {
            //代表复杂逻辑执行了一段时间
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("售票结束");
    }
 
    public static void sellTicketWithLock() throws KeeperException, InterruptedException, IOException {
    	DistributedLock lock = new DistributedLock();
        lock.acquireLock();//获取锁
        sell();//售票
        lock.releaseLock();//释放锁
    }
 
    public  static void main(String[] args) throws KeeperException, InterruptedException, IOException {
        for(int i=0;i<100;i++){
        	sellTicketWithLock();
        }
    }
}
