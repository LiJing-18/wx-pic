package com.karat.cn.other.redis.lock.demo;

import com.karat.cn.other.redis.demo.RedisManager;
import com.karat.cn.other.redis.lock.dao.RedisDistributeLock;
import com.karat.cn.other.redis.lock.dao.impl.DefaultRedisDistributeLock;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author Administrator
 *
 */
public class TestRedisDistributeLock {

    public static void main(String[] args) {
        for (int i = 1; i < 100; i ++) {
            new TestLock().start();
        }
    }

    static class TestLock extends Thread {

        static RedisDistributeLock locker = new DefaultRedisDistributeLock();

        @Override
        public void run() {
			try {
				Jedis jedis = RedisManager.getJedis();//连接获取jedis实列
				locker.lock(jedis, "aone", "123");
	            // TODO 模拟业务
	            System.out.println(Thread.currentThread().getName() + "获取锁");
	            try {
	                Thread.sleep(100);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            System.out.println(Thread.currentThread().getName() + "释放锁");

	            locker.release(jedis, "aone", "123");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }
}
