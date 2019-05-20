package com.news.sina.dao;

import com.news.sina.common.RedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 * 读写缓存命中率数据
 **/
@Repository
public class CacheHitDao {

    @Autowired
    private RedisTemplate redisTemplate;


    private static final String CACHE_HIT_KEY = "cache-hit";
    private static final String CACHE_MISS_KEY = "cache-miss";

    public void hit() {
        try (Jedis jedis = RedisPool.getResource()) {
            jedis.incr(CACHE_HIT_KEY);
        }
    }

    public void miss() {
        try (Jedis jedis = RedisPool.getResource()) {
            jedis.incr(CACHE_MISS_KEY);
        }
    }
}
