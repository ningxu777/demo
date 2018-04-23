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
        sentinelAddrs.add(redis_sentinel_master);
        sentinelAddrs.add(redis_sentinel_slave);
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
    public void set(String key, String value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
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
}
