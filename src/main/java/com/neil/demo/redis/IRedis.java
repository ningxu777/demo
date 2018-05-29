package com.neil.demo.redis;


import com.neil.demo.properties.MyProperties;

/**
 * Created by Neil on 2018/4/23.
 */
public interface IRedis {

    String redis_master = MyProperties.getByKey("redis_master");
    String redis_slave = MyProperties.getByKey("redis_slave");
    String redis_sentinel = MyProperties.getByKey("redis_sentinel");
    String redis_cluster = MyProperties.getByKey("redis_cluster");

    void set(String key, String value);

    void set(String key, String value, Integer expire);

    String get(String key);

    void delete(String key);
}
