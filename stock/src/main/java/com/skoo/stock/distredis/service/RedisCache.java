package com.skoo.stock.distredis.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

@Component
public class RedisCache {
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 计数器增
     */
    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    /**
     * 计数器增(频度)
     */
    public Long incrBy(String key, long cnt) {
        return jedisCluster.incrBy(key, cnt);
    }

    /**
     * 计数器减
     */
    public Long decr(String key) {
        return jedisCluster.decr(key);
    }

    /**
     * 计数器减(频度)
     */
    public Long decrBy(String key, long cnt) {
        return jedisCluster.decrBy(key, cnt);
    }

    /**
     * 设置生命周期
     */
    public Long expire(String key, int time) {
        return jedisCluster.expire(key, time);
    }

    public void setCount(String key, String value) {
        jedisCluster.set(key, value);
    }

    public void getCount(String key) {
        jedisCluster.get(key);
    }

    public void set(String key, Object object) {
        jedisCluster.set(key, JSON.toJSONString(object));
    }

    public void set(String key, Object object, int time) {
        expire(key, time);
        jedisCluster.set(key, JSON.toJSONString(object));
    }

    public String get(String key) {
        return jedisCluster.get(key);
    }

    public <T> T get(String key, Class<T> clazz) {
        String text = get(key);
        return JSON.parseObject(text, clazz);
    }

    public String getString(String key) {
        return get(key, String.class);
    }

    public void del(String key, String... field) {
        jedisCluster.del(key);
    }

    /**
     * 查询键
     */
    public boolean exists(String key) {
        return jedisCluster.exists(key);
    }

}
