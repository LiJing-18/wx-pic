package com.karat.cn.other.redis.dao.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.karat.cn.other.redis.dao.JedisClient;

import redis.clients.jedis.JedisCluster;
/**
 * 集群实现类 
 * @author Administrator
 *
 */
public class JedisClientClusterImpl implements JedisClient {
 
    @Autowired
    private JedisCluster jedisCluster;
     
    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }
 
    @Override
    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }
 
    @Override
    public String hget(String hkey, String key) {
        return jedisCluster.hget(hkey, key);
    }
 
    @Override
    public long hset(String hkey, String key, String value) {
        return jedisCluster.hset(hkey, key, value);
    }
 
    @Override
    public long incr(String key) {
        return jedisCluster.incr(key);
    }
 
    @Override
    public long expire(String key, int second) {
        return jedisCluster.expire(key, second);
    }
 
    @Override
    public long ttl(String key) {
        return jedisCluster.ttl(key);
    }
 
    @Override
    public long del(String key) {     
        return jedisCluster.del(key);
    }
 
    @Override
    public long hdel(String hkey, String key) {   
        return jedisCluster.hdel(hkey, key);
    }

	@Override
	public Set<String> allKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sadd(String key, Set<String> list) {
		// TODO Auto-generated method stub
	}

	@Override
	public Set<String> smembers(String key) {
		// TODO Auto-generated method stub
		return null;
	}


}