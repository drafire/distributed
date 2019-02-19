package com.drafire.distributed.redis;

import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Publisher extends Thread {
    @Override
    public void run() {
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
        Jedis jedis=RedisHelper.getJedisByPool();

        while (true){
            String line=null;
            try {
                line=reader.readLine();
                if(!"quit".equalsIgnoreCase(line)){
                    jedis.publish("mychannel",line);
                }else{
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
