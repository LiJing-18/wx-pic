package com.karat.cn.other.redis.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * 客户端连接redis
 * @author 开发
 *
 */
public class RedisManager {

	private static JedisPool jedisPool;
	
	static {
		JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(20);//最大连接数, 默认8个
		jedisPoolConfig.setMaxIdle(10);//最小空闲连接数, 默认0
		jedisPool=new JedisPool(jedisPoolConfig, "47.107.121.215",6379);
	}
	
	public static Jedis getJedis() throws Exception {
		if(jedisPool!=null) {
			//getResource()方法从连接池中取得一个Jedis实例
			return jedisPool.getResource();
		}
		throw new Exception("jedis启动异常");
	}
}
