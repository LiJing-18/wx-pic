package com.karat.cn.other.redis.dao.impl;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.karat.cn.other.redis.dao.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * 单机实现类
 * @author Administrator
 *
 */
public class JedisClientImpl implements JedisClient{
	
	@Autowired
    private JedisPool jedisPool;
    
    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.get(key);
        jedis.close();
        return string;
    }
 
    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.set(key, value);
        jedis.close();
        return string;
    }
 
    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.hget(hkey, key);
        jedis.close();
        return string;
    }
 
    @Override
    public long hset(String hkey, String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(hkey, key, value);
        jedis.close();
        return result;
    }
 
    @Override
    public long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.incr(key);
        jedis.close();
        return result;
    }
 
    @Override
    public long expire(String key, int second) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key, second);
        jedis.close();
        return result;
    }
 
    @Override
    public long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }
 
    @Override
    public long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }
 
    @Override
    public long hdel(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hdel(hkey, key);
        jedis.close();
        return result;
    }

	@Override
	public Set<String> allKey() {
		// TODO Auto-generated method stub
		Jedis jedis=jedisPool.getResource();
		return jedis.keys("*");
	}

	@Override
	public void sadd(String key, Set<String> list) {
		// TODO Auto-generated method stub
		Jedis jedis=jedisPool.getResource();
		list.forEach(i->{
			jedis.sadd(key, i);
		});
	}

	@Override
	public Set<String> smembers(String key) {
		// TODO Auto-generated method stub
		Jedis jedis=jedisPool.getResource();
		return jedis.smembers(key);
	}
}
