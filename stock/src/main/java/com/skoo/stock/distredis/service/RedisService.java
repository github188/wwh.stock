package com.skoo.stock.distredis.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.*;

@Component
@SuppressWarnings("unchecked")
public class RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    private Logger log = Logger.getLogger(RedisService.class);

    /**
     * 查询一个key是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean exists(String key) {
        boolean flag = false;
        try {
            flag = redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("redis异常");
            log.error(e.getMessage());
        }
        return flag;
    }

    /**
     * 设置一个key的过期的秒数
     *
     * @param key 键
     * @param time 过期时间
     * @return 设置是否成功
     */
    public boolean expire(String key, int time) {
        final String inkey = key;
        final int intime = time;
        boolean flag = false;
        try {
            flag = (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection conn) throws DataAccessException {
                    boolean doflag = false;
                    byte[] bkey = redisTemplate.getStringSerializer().serialize(inkey);
                    if (exists(inkey)) {
                        doflag = conn.expire(bkey, intime);
                    }
                    return doflag;
                }
            });
        } catch (Exception e) {
            log.error("redis异常");
            log.error(e.getMessage());
        }
        return flag;
    }

    /**
     * 判断给定域是否存在于哈希集中
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean hexists(String key, String field) {
        final String inkey = key;
        final String infield = field;
        boolean flag = false;
        try {
            flag = (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection conn) throws DataAccessException {
                    boolean doflag = false;
                    if (exists(inkey)) {
                        byte[] bkey = redisTemplate.getStringSerializer().serialize(inkey);
                        byte[] bfield = redisTemplate.getStringSerializer().serialize(infield);
                        doflag = conn.hExists(bkey, bfield);
                    }

                    return doflag;
                }
            });
        } catch (Exception e) {
            log.error("redis异常");
            log.error(e.getMessage());
        }
        return flag;
    }

    /**
     * 删除一个key
     *
     * @param key 键
     * @return 是否删除成功
     */
    public boolean del(String key) {
        boolean flag = false;
        try {
            if (exists(key)) {
                redisTemplate.delete(key);
                flag = true;
            }
        } catch (Exception e) {
            log.error("redis异常");
            log.error(e.getMessage());
        }
        return flag;
    }

    /**
     * 获取key的值
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        final String inkey = key;
        String result = null;
        try {
            result = (String) redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection conn) throws DataAccessException {
                    byte[] bkey = redisTemplate.getStringSerializer().serialize(inkey);
                    String str = null;
                    if (exists(inkey)) {
                        byte[] bvalue = conn.get(bkey);
                        if (bvalue != null && bvalue.length > 0) {
                            str = redisTemplate.getStringSerializer().deserialize(bvalue);
                        }
                    }
                    return str;
                }
            });
        } catch (Exception e) {
            log.error("redis异常[get]");
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 设定key的值
     *
     * @param key 键
     * @return  是否设置成功
     */
    public boolean set(String key, String value) {
        final String inkey = key;
        final String invalue = value;
        boolean flag = false;
        try {
            flag = (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection conn) throws DataAccessException {
                    boolean doflag = false;
                    byte[] bkey = redisTemplate.getStringSerializer().serialize(inkey);
                    byte[] bvalue = redisTemplate.getStringSerializer().serialize(invalue);
                    conn.set(bkey, bvalue);
                    if (exists(inkey)) {
                        doflag = true;
                    }
                    return doflag;
                }
            });
        } catch (Exception e) {
            log.error("redis异常[set]");
            log.error(e.getMessage());
        }
        return flag;
    }


    /**
     * 删除HashMap指定键值
     *
     * @param key 键
     * @return 删除是否成功
     */
    public boolean hdel(String key, String field) {
        final String inkey = key;
        final String infield = field;
        boolean flag = false;
        try {
            flag = (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection conn) throws DataAccessException {
                    boolean doflag = false;
                    byte[] bkey = redisTemplate.getStringSerializer().serialize(inkey);
                    byte[] bfield = redisTemplate.getStringSerializer().serialize(infield);
                    if (exists(inkey)) {
                        doflag = conn.hDel(bkey, bfield) != null;
                    }
                    return doflag;
                }
            });
        } catch (Exception e) {
            log.error("redis异常[hdel]");
            log.error(e.getMessage());
        }
        return flag;
    }

    /**
     * 添加map键值对
     *
     * @param key 键
     * @param field Map键
     * @param value Map值
     * @return 设定是否成功
     */
    public boolean hset(String key, String field, String value) {
        final String inkey = key;
        final String infield = field;
        final String invalue = value;
        boolean flag = false;
        try {
            flag = (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection conn) throws DataAccessException {
                    boolean doflag = false;
                    byte[] bkey = redisTemplate.getStringSerializer().serialize(inkey);
                    byte[] bfield = redisTemplate.getStringSerializer().serialize(infield);
                    byte[] bvalue = redisTemplate.getStringSerializer().serialize(invalue);
                    if (exists(inkey)) {
                        doflag = conn.hSet(bkey, bfield, bvalue);
                    }
                    return doflag;
                }
            });
        } catch (Exception e) {
            log.error("redis异常[hset]");
            log.error(e.getMessage());
        }
        return flag;
    }

    /**
     * 获取hash里面指定字段的值
     *
     * @param key 键
     * @param field Hash键
     * @return Hash键对应值
     */
    public String hget(String key, String field) {
        final String inkey = key;
        final String infield = field;
        String result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<String>() {
                public String doInRedis(RedisConnection conn) throws DataAccessException {
                    byte[] bkey = redisTemplate.getStringSerializer().serialize(inkey);
                    byte[] bfield = redisTemplate.getStringSerializer().serialize(infield);
                    String str = null;
                    if (exists(inkey)) {
                        byte[] bvalue = conn.hGet(bkey, bfield);
                        if (bvalue != null && bvalue.length > 0) {
                            str = redisTemplate.getStringSerializer().deserialize(bvalue);
                        }
                    }
                    return str;
                }
            });
        } catch (Exception e) {
            log.error("redis异常[hget]");
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 获取hash里面指定字段的值
     *
     * @param key 键
     * @return  列表
     */
    public List<String> hmget(String key, List<String> fields) {
        final String inkey = key;
        final List<String> infields = fields;
        List<String> result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<List<String>>() {
                public List<String> doInRedis(RedisConnection conn) throws DataAccessException {
                    byte[] bkey = redisTemplate.getStringSerializer().serialize(inkey);
                    byte[][] bfieldArr = new byte[infields.size()][];
                    for (int i = 0; i < infields.size(); i++) {
                        bfieldArr[i] = redisTemplate.getStringSerializer().serialize(infields.get(i));
                    }
                    List<String> strList = null;
                    if (exists(inkey)) {
                        List<byte[]> valueList = conn.hMGet(bkey, bfieldArr);
                        if (valueList != null && valueList.size() > 0) {
                            strList = new ArrayList<>();
                            for (byte[] value : valueList) {
                                strList.add(redisTemplate.getStringSerializer().deserialize(value));
                            }
                        }
                    }
                    return strList;
                }
            });
        } catch (Exception e) {
            log.error("redis异常[hmget]");
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 设置hash字段值
     *
     * @param key 键
     *
     */
    public void hmset(String key, Map<String, String> map) {
        final String inkey = key;
        final Map<String, String> inmap = map;
        try {
            redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection conn) throws DataAccessException {

                    byte[] bkey = redisTemplate.getStringSerializer().serialize(inkey);
                    Map<byte[], byte[]> bmap = new HashMap<>();
                    for (String mapkey : inmap.keySet()) {
                        byte[] bmapkey = redisTemplate.getStringSerializer().serialize(mapkey);
                        byte[] bmapvalue = redisTemplate.getStringSerializer().serialize(inmap.get(mapkey));
                        bmap.put(bmapkey, bmapvalue);
                    }
                    conn.hMSet(bkey, bmap);
                    return null;
                }
            });
        } catch (Exception e) {
            log.error("redis异常[hmset]");
            log.error(e.getMessage());
        }
    }

    /**
     * 获取HashMap中所有键集合
     *
     * @param key 键
     * @return Set集
     */
    public Set<String> hkeys(String key) {
        final String inkey = key;
        Set<String> result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Set<String>>() {
                public Set<String> doInRedis(RedisConnection conn) throws DataAccessException {
                    Set<String> strSet = null;
                    if (exists(inkey)) {
                        byte[] bkey = redisTemplate.getStringSerializer().serialize(inkey);
                        Set<byte[]> bmapkyes = conn.hKeys(bkey);
                        if (bmapkyes != null && bmapkyes.size() > 0) {
                            strSet = new HashSet<>();
                            for (byte[] bmapkey : bmapkyes) {
                                strSet.add(redisTemplate.getStringSerializer().deserialize(bmapkey));
                            }
                        }
                    }
                    return strSet;
                }
            });
        } catch (Exception e) {
            log.error("redis异常[hkeys]");
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 获得hash的所有值
     *
     * @param key 键
     * @return  列表
     */
    public List<String> hvals(String key) {
        final String inkey = key;
        List<String> result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<List<String>>() {
                public List<String> doInRedis(RedisConnection conn) throws DataAccessException {
                    byte[] bkey = redisTemplate.getStringSerializer().serialize(inkey);
                    List<String> strList = null;
                    if (exists(inkey)) {
                        List<byte[]> valueList = conn.hVals(bkey);
                        if (valueList != null && valueList.size() > 0) {
                            strList = new ArrayList<>();
                            for (byte[] value : valueList) {
                                strList.add(redisTemplate.getStringSerializer().deserialize(value));
                            }
                        }
                    }
                    return strList;
                }
            });
        } catch (Exception e) {
            log.error("redis异常[hvals]");
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 获取HashMap所有键值对
     *
     * @param key 键
     * @return Map
     */
    public Map<String, String> hgetAll(String key) {
        final String inkey = key;
        Map<String, String> result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Map<String, String>>() {
                public Map<String, String> doInRedis(RedisConnection conn) throws DataAccessException {
                    byte[] bkey = redisTemplate.getStringSerializer().serialize(inkey);
                    Map<String, String> strMap = null;
                    if (exists(inkey)) {
                        Map<byte[], byte[]> bmap = conn.hGetAll(bkey);
                        if (bmap != null && bmap.size() > 0) {
                            strMap = new HashMap<>();
                            for (byte[] key : bmap.keySet()) {
                                String mapkey = redisTemplate.getStringSerializer().deserialize(key);
                                String mapvalue = redisTemplate.getStringSerializer().deserialize(bmap.get(key));
                                strMap.put(mapkey, mapvalue);
                            }
                        }
                    }
                    return strMap;
                }
            });
        } catch (Exception e) {
            log.error("redis异常[hgetAll]");
            log.error(e.getMessage());
        }
        return result;
    }


    public void set2(String key, String value)
    {
        ShardedJedis jedis =  shardedJedisPool.getResource();
        jedis.set(key, value);
    }

    public String get2(String key)
    {
        ShardedJedis jedis =  shardedJedisPool.getResource();
        return jedis.get(key);
    }
}
