package com.karat.cn.redis.demo;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
			Jedis jedis=RedisManager.getJedis();//连接获取jedis实列		
			String value=UUID.randomUUID().toString();//随机设置值UUID(值无所谓)			
			//连接超时时间，到了该设置时间，锁还没有被释放(没有抢到)，结束
			long end=System.currentTimeMillis()+timeout;
			
			while(System.currentTimeMillis()<end) {//阻塞
				/**
				 * setnx
				 * 返回整数，具体为 
				 * 1，当 key 的值被设置 
				 * 0，当 key 的值没被设置
				 */
				if(jedis.setnx(key, value)==1) {//插入数据成功
					//设置key超时时间
					jedis.expire(key, timeout);			
					//锁设置成功,redis操作成功
					return value;
				}
				//获得key的过期时间
				if(jedis.ttl(key)==1){
					jedis.expire(key, timeout);
				}				
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
			Jedis jedis=RedisManager.getJedis();//连接获取jedis实列
			while(true) {
				jedis.watch(key);
				//判断获得锁的线程和当前线程redis中存的值一样
				if(value.equals(jedis.get(key))) {
					Transaction transaction=jedis.multi();//去开启事务
					transaction.del(key);//删除key
					List<Object> list=transaction.exec();//执行事务
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
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String args[]) {
		 RedisLock lock=new RedisLock();
		 //线程池
		 ExecutorService service = Executors.newFixedThreadPool(10);
	        for (int i = 0;i<100;i++){
	            service.execute(()-> {
	            	String value=lock.getLock("aaa", 10000);
	            	try {
		            	if(null!=value) {
		        			System.out.println("获取锁成功");
		        			Thread.sleep(1000);
		        		}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						System.out.println("释放锁");
	        			lock.delLock("aaa", value);
	        			System.out.println("已释放");
					}
	            });
	        }
	    service.shutdown();
	}
}
