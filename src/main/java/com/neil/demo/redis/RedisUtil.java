package com.neil.demo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Neil on 2018/4/20.
 * JedisPool
 */
public class RedisUtil implements IRedis{

    public static  JedisPool pool = null;

    static{

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最链接数
        poolConfig.setMaxTotal(10);
        //最大空闲数
        poolConfig.setMaxIdle(2);
        //最大等待时长，超过这个时长还未获取到连接则抛JedisException异常
        poolConfig.setMaxWaitMillis(1000); //等1分钟

        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        poolConfig.setTestOnBorrow(true);
        String[] address= redis_master.split(":");
        String host = address[0];
        int port = Integer.valueOf(address[1]);
        pool = new JedisPool(poolConfig,host,port);

    }

    /**
     * 单机模式或者主从模式通过Redis链接池获取Jedis
     * @return
     */
    public Jedis getJedis(){

        Jedis jedis = RedisUtil.pool.getResource();
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
    public void set(String key, String value, Integer seconds) {

        Jedis jedis = getJedis();
        try {
            jedis.setex(key, seconds,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    @Override
    public String get(String key){
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
