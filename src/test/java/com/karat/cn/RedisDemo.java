package com.karat.cn;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.karat.cn.redis.dao.JedisClient;
import com.karat.cn.redis.redisKey.RedisKey;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:redis.xml")
public class RedisDemo {
	
	@Autowired
	private JedisClient redis;
	
	@Test
	public void redisTest(){
		Set<String> list=redis.allKey();
		list.forEach(i->{
			System.out.println(i);
		});
	}
	@Test
	public void redisList(){
		Set<String> list=redis.smembers(RedisKey.TAG);
		list.forEach(i->{
			System.out.println(i);
		});
	}
}
