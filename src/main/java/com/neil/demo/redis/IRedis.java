package com.neil.demo.redis;

import com.neil.demo.properties.MyProperties;

/**
 * Created by Neil on 2018/4/23.
 */
public interface IRedis {

    public static final String redis_master = MyProperties.getByKey("redis_master");
    public static final String redis_slave = MyProperties.getByKey("redis_slave");
    public static final String redis_sentinel_master = MyProperties.getByKey("redis_sentinel_master");
    public static final String redis_sentinel_slave = MyProperties.getByKey("redis_sentinel_slave");

    void set(String key, String value);

    String get(String key);
}
