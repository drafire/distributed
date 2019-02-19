package com.drafire.distributed.redis;

import redis.clients.jedis.Jedis;

public class SubThread extends Thread {

    private final Subscriber subscriber = new Subscriber();
    private static final String channel = "mychannel";

    @Override
    public void run() {
        Jedis jedis=null;

        try {
            jedis=RedisHelper.getJedisByPool();
            jedis.subscribe(subscriber,channel);  //通过subscribe 的api去订阅，入参是订阅者和频道名
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null!=jedis){
                jedis.close();
            }
        }
    }
}
