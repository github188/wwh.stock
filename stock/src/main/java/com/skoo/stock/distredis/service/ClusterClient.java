package com.skoo.stock.distredis.service;

import com.alibaba.fastjson.JSON;
import com.skoo.stock.sys.utils.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.BinaryJedisCluster;
import redis.clients.jedis.JedisCluster;

import java.util.Map;

/**
 * @author zhangwei_david
 * @version $Id: RedisClient.java, v 0.1 2015年6月6日 下午6:35:25 zhangwei_david Exp $
 */
@Component
@SuppressWarnings("unchecked")
public class ClusterClient {

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    BinaryJedisCluster binaryJedisCluster;

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

    /**
     * 查询键
     */
    public boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    /**
     * 删除键值
     */
    public Long del(String key) {
        return jedisCluster.del(key);
    }

    /**
     * 设定map
     */
    public boolean hset(String key, Map map, int expireTime) {
        jedisCluster.expire(key, expireTime);
        String result = jedisCluster.set(key, JsonUtils.bean2Api(map));

        return "OK".equals(result);
    }

    /**
     * 设定map
     */
    public boolean hset(String key, Map map) {
        String result = jedisCluster.set(key, JsonUtils.bean2Api(map));

        return "OK".equals(result);
    }


    /**
     * 设定字符串
     */
    public boolean set(String key, String value, int expireTime) {
        jedisCluster.expire(key, expireTime);
        String result = jedisCluster.set(key, value);

        return "OK".equals(result);
    }

    /**
     * 设定字符串无期限
     */
    public boolean set(String key, String value) {
        String result = jedisCluster.set(key, value);

        return "OK".equals(result);
    }

    /**
     * 获取字符串
     */
    public Map hget(String key) {
        return JsonUtils.parseJSON2Map(jedisCluster.get(key));
    }

    /**
     * 获取字符串
     */
    public String get(String key) {
        return jedisCluster.get(key);
    }


    /**
     * 设置生命周期
     */
    public Long bexpire(String key, int time) {
        return binaryJedisCluster.expire(key.getBytes(), time);
    }


    /**
     * 查询键
     */
    public boolean bexists(String key) {
        return binaryJedisCluster.exists(key.getBytes());
    }

    /**
     * 删除键值
     */
    public Long bdel(String key) {
        return binaryJedisCluster.del(key.getBytes());
    }

    /**
     * 字节流有期限（序列化）
     */
    public boolean bset(String key, Object obj, long expireTime) {
        byte[] bytes = SerializationUtil.serializer(obj);

        String result = binaryJedisCluster.set(key.getBytes(), bytes, "nx".getBytes(), "ex".getBytes(), expireTime);

        return "OK".equals(result);
    }

    /**
     * 字节流无期限
     */
    public boolean bset(String key, Object obj) {
        byte[] bytes = SerializationUtil.serializer(obj);

        String result = binaryJedisCluster.set(key.getBytes(), bytes);

        return "OK".equals(result);
    }

    /**
     * 获取对象（反序列化）
     */
    public <T> T bget(String key, Class<T> clazz) {
        byte[] bytes = binaryJedisCluster.get(key.getBytes());
        return SerializationUtil.deserializer(bytes, clazz);
    }

    /**
     * 把对象放入Hash中
     */
    public long hset(String key, String fieldKey, Object obj) {
        return jedisCluster.hset(key, fieldKey, JSON.toJSONString(obj));
    }

    /**
     * 从Hash中获取对象
     */
    public String hget(String key, String field) {
        return jedisCluster.hget(key, field);
    }

    /**
     * 从Hash中获取对象,转换成制定类型
     */
    public <T> T hget(String key, String field, Class<T> clazz) {
        String text = hget(key, field);
        return JSON.parseObject(text, clazz);
    }

    /**
     * 从Hash中删除对象
     */
    public void hdel(String key, String... field) {
        jedisCluster.hdel(key, field);
    }


}