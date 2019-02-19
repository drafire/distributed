package com.drafire.distributed.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisHelper {
    private static final String HOST = "192.168.109.128";
    private static final Integer PORT = 6379;

    private static final String password = "123456";

    public static Jedis getJedis() {
        Jedis jedis = new Jedis(HOST, PORT);
        jedis.auth(password);
        return jedis;
    }

    public static Jedis getJedis(int dbIndex){
        Jedis jedis = new Jedis(HOST, PORT);
        jedis.auth(password);
        return jedis;
    }

    /**
     * 使用池化，会较好地避免客户端连接服务器过多的问题
     * 建议使用这种方式获取redis
     * @return
     */
    public static Jedis getJedisByPool() {
        JedisPool jedisPool = new JedisPool(HOST, PORT);
        if (null != jedisPool) {
            Jedis resource = jedisPool.getResource();
            resource.auth(password);
            return resource;
        }
        return null;
    }
}
