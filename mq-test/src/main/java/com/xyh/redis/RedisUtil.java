package com.xyh.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * @author xuyh
 * @date 2019/7/12
 */
public class RedisUtil {

    private Jedis jedis;
    private JedisPool jedisPool;

    public RedisUtil(){
        initPool();
        jedis=jedisPool.getResource();
    }

    private void initPool() {
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(4);
        jedisPoolConfig.setMaxTotal(8);
        jedisPoolConfig.setMaxWaitMillis(1000L);
        jedisPoolConfig.setTestOnBorrow(false);
        jedisPool=new JedisPool(jedisPoolConfig,"192.168.40.210",6379, 10000,"123456");
    }

    public String get(String key){
        return jedis.get(key);
    }
    public void set(String key,String value){
        jedis.set(key,value);
    }
    public Set<String> keys(String pattern){
        Set<String> keys = jedis.keys(pattern);
        return keys;
    }
}
