package com.neil.demo.redis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Neil on 2018/4/23.
 * JedisSentinelPool
 */
@Component
public class RedisSentinelUtil implements IRedis {

    public static JedisSentinelPool pool = null;

    static {
        Set<String> sentinelAddrs = new HashSet<String>();
        String[] redis_sentinelArr = redis_sentinel.split(",");
        for(String item:redis_sentinelArr){
            sentinelAddrs.add(item);
        }
        pool = new JedisSentinelPool("mymaster",sentinelAddrs);
    }

    /**
     * 主从模式还可以通过哨兵连接池获取Jedis
     * @return
     */
    public Jedis getJedis(){
        Jedis jedis = null;
        jedis = RedisSentinelUtil.pool.getResource();
        return jedis;
    }


    @Override
    public void set(String key, String value,Integer seconds) {
        Jedis jedis = getJedis();
        try {
            if(seconds == null){
                jedis.set(key, value);
            }else{
                jedis.setex(key, seconds, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    @Override
    public String get(String key) {
        String value = null;
        Jedis jedis = getJedis();
        try {
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            jedis.close();
        }
        return value;
    }

    @Override
    public void delete(String key) {
        String value = null;
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            jedis.close();
        }
    }
}
