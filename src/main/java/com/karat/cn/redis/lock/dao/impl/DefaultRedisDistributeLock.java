package com.karat.cn.redis.lock.dao.impl;

import com.karat.cn.redis.lock.dao.RedisDistributeLock;
import com.karat.cn.redis.lock.util.RedisTool;

import redis.clients.jedis.Jedis;


public class DefaultRedisDistributeLock implements RedisDistributeLock {

    //默认 非公平锁
    private static final Boolean DEFALUT_FAIR = false;
    //过期时间 默认 10 秒, 太短会导致锁不住， 如果业务无法在指定过期时间内 完成， 则必须加长过期时间
    private static final Integer DEFAULT_EXPIRE_TIME = 10000;

    private Boolean isFair;

    private Integer expireTime;
    //无参构造
    public DefaultRedisDistributeLock() {
        isFair = DEFALUT_FAIR;
        expireTime = DEFAULT_EXPIRE_TIME;
    }
    //带参构造
    public DefaultRedisDistributeLock(boolean isFair, Integer expireTime) {
        this.isFair = isFair;
        this.expireTime = expireTime;
    }
    
    //公平锁
    @Override
    public void fairLock(Jedis jedis, String key, String uuid) {
        // 通过一个队列维护， 参照 AQS 实现
    }
    //非公平锁
    @Override
    public void unfairLock(Jedis jedis, String key, String uuid) {
        while (true) {
            boolean locked = RedisTool.tryGetDistributedLock(jedis, key, uuid, expireTime);
            if (locked) {
                break;
            }
        }
    }
    //上锁(isFair为false默认非公平锁)
    @Override
    public void lock(Jedis jedis, String key, String uuid) {
        if (isFair) {
        	//公平锁
            fairLock(jedis, key, uuid);
        } else {
        	//非公平锁
            unfairLock(jedis, key, uuid);
        }
    }
    //解锁
    @Override
    public void release(Jedis jedis, String key, String uuid) {
        try {
            while (true) {
                boolean released = RedisTool.releaseDistributedLock(jedis, key, uuid);
                if (released) {
                    break;
                }
            }
        } finally {
            jedis.close();
        }
    }
}
