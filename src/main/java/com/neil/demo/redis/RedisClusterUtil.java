package com.neil.demo.redis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

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
        nodes.add(new HostAndPort("127.0.0.1",6379));
        nodes.add(new HostAndPort("127.0.0.1",6380));
        nodes.add(new HostAndPort("127.0.0.1",6381));

        JedisCluster cluster = new JedisCluster(nodes, poolConfig);

        return cluster;
    }

    @Override
    public void set(String key, String value) {

    }

    @Override
    public String get(String key) {
        return null;
    }
}
