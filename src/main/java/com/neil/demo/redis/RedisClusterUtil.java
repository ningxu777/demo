package com.neil.demo.redis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Neil on 2018/4/23.
 * JedisCluster
 */
@Component
public class RedisClusterUtil implements IRedis{

    /**
     * 集群模式下,直接获取JedisCluster
     */
    public static JedisCluster getJedisCluster(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最链接数
        poolConfig.setMaxTotal(10);
        //最大空闲数
        poolConfig.setMaxIdle(2);
        //最大等待时长，超过这个时长还未获取到连接则抛JedisException异常
        poolConfig.setMaxWaitMillis(1000); //等1分钟

        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        String[] redis_clusterArr = redis_cluster.split("");
        for(String item:redis_clusterArr){
            String[] itemArr = item.split(",");
            nodes.add(new HostAndPort(itemArr[0],Integer.valueOf(itemArr[1])));
        }
        JedisCluster cluster = new JedisCluster(nodes, poolConfig);

        return cluster;
    }

    @Override
    public void set(String key, String value,Integer seconds) {
        JedisCluster cluster = getJedisCluster();
        try{
            if(seconds == null){
                cluster.set(key,value);
            }else{
                cluster.setex(key,seconds,value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                cluster.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String get(String key) {
        String value = null;
        JedisCluster cluster = getJedisCluster();
        try {
            value = cluster.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cluster.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    @Override
    public void delete(String key) {
        JedisCluster cluster = getJedisCluster();
        try {
            cluster.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cluster.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
