package com.karat.cn.zkLock.javaapilock.lockDemo;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class Test {
	
	private void sell(int i){
        System.out.println(i+"售票开始");
        //线程随机休眠数毫秒，模拟现实中的费时操作
        int sleepMillis = (int) (Math.random() * 2000);
        try {
            //代表复杂逻辑执行了一段时间
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(i+"售票结束");
    }
 
    public void sellTicketWithLock(int i) throws KeeperException, InterruptedException, IOException {
    	Lock lock = new Lock();
        lock.acquireLock();//获取锁
        sell(i);//售票
        lock.releaseLock();//释放锁
    }
 
    public void main(String[] args) throws KeeperException, InterruptedException, IOException {
        for(int i=0;i<100;i++){
        	sellTicketWithLock(i);
        }
    }
}
