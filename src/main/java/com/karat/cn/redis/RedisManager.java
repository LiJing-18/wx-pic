package com.karat.cn.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {

	private static JedisPool jedisPool;
	
	static {
		JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(20);
		jedisPoolConfig.setMaxIdle(10);
		jedisPool=new JedisPool(jedisPoolConfig, "47.107.121.215",6379);
	}
	
	public static Jedis getJedis() throws Exception {
		if(jedisPool!=null) {
			return jedisPool.getResource();
		}
		throw new Exception("jedis启动异常");
	}
}
