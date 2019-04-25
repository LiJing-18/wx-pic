package com.karat.cn.other.redis.dao;

import java.util.Set;

public interface JedisClient {
	/**
	 * 字符类型
	 */
	//读
	String get(String key);
	//写
	String set(String key,String value);
	/**
	 * 散列类型(比较适合存储对象)
	 */
	//读
	String hget(String hkey,String key);
	//写
	long hset(String hkey,String key,String value);
	//key键的value递增
	long incr(String key);
	//设置键过期时间
	long expire(String key,int second);
	//获取key过期时间
	long ttl(String key);
	//删除key
	long del(String key);
	//散列类型中的删除一个或多个字段
	long hdel(String hkey,String key);
	//查看所有键
	Set<String> allKey();
	/**
	 * 集合类型
	 */
	//无序集合添加
	void sadd(String key,Set<String> list);
	//无序集合查所有数据
	Set<String> smembers(String key); 
}
