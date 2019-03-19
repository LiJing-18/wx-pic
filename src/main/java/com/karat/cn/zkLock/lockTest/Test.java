package com.karat.cn.zkLock.lockTest;

import java.util.concurrent.CountDownLatch;

public class Test {
	
	 static Integer ALL = 10;
	 
	 public static void main(String[] args) throws InterruptedException {
	      CountDownLatch countDownLatch = new CountDownLatch(10);
	      for (int i = 1; i <= 10; i++) {
	           new Thread(() -> {
	                 try {
	                     countDownLatch.await();
	                 } catch (InterruptedException e) {
	                     e.printStackTrace();
	                 }
	                 DistributedLock lock= new DistributedLock();
	                 lock.lock();//上锁
	                 System.out.println(Thread.currentThread().getName() + "卖出第" + ALL-- + "张票!");
	                 lock.unlock();//释放锁
	           }, "售票员<" + i + ">").start();
	           countDownLatch.countDown();
	      }
	      Thread.sleep(1500);
	      ALL=10;
	      System.out.println("========================"); 
	}
}
