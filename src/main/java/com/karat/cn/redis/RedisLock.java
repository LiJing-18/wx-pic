package com.karat.cn.redis;

import java.util.List;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RedisLock {
	/**
	 * 上锁
	 * @param key	锁key值
	 * @param timeout	获取请求时间(多长时间获取不到锁表示失败)
	 * @return
	 */
	public String getLock(String key,int timeout) {
		try {
			Jedis jedis=RedisManager.getJedis();
			String value=UUID.randomUUID().toString();
			
			long end=System.currentTimeMillis()+timeout;

			while(System.currentTimeMillis()<end) {//阻塞
				if(jedis.setnx(key, value)==1) {
					return value;
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 释放锁
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean delLock(String key,String value) {
		try {
			Jedis jedis=RedisManager.getJedis();
			while(true) {
				jedis.watch(key);
				//判断获得锁的线程和当前线程redis中存的值一样
				if(value.equals(jedis.get(key))) {
					Transaction transaction=jedis.multi();
					transaction.del(key);
					List<Object> list=transaction.exec();
					if(list==null) {
						continue;
					}
					return true;
				}
				jedis.unwatch();
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String args[]) {
		RedisLock lock=new RedisLock();
		String lookId=lock.getLock("aaa", 10000);
		
		if(null!=lookId) {
			System.out.println("获取锁成功");
		}
		System.out.println("失败");
		String mm=lock.getLock("aaa", 10000);
		System.out.println(mm);
	}
}
