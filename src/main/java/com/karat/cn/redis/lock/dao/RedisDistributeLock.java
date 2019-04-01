package com.karat.cn.redis.lock.dao;

import redis.clients.jedis.Jedis;


public interface RedisDistributeLock {

    /**
     * 公平锁， 只能说 本机的公平
     * @param jedis
     * @param key
     * @param uuid
     */
    void fairLock(Jedis jedis, String key, String uuid);

    /**
     * 非公平锁
     * @param jedis
     * @param key
     * @param uuid
     */
    void unfairLock(Jedis jedis, String key, String uuid);

    /**
     * 上锁
     * @param jedis
     * @param key
     * @param uuid
     */
    void lock(Jedis jedis, String key, String uuid);

    /**
     * 解锁
     * @param jedis
     * @param key
     * @param uuid
     */
    void release(Jedis jedis, String key, String uuid);
}
