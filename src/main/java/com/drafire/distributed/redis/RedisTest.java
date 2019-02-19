package com.drafire.distributed.redis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RedisTest {

    private final static Jedis jedis;

    static {
        jedis = RedisHelper.getJedisByPool();
    }

    public static void main(String[] args) {
        //字符串
        jedis.set("hello", "world");
        System.out.println(jedis.get("hello"));

        //哈希
        jedis.hset("person", "field1", new Person(1, "drafire").toString());
        System.out.println(jedis.hget("person", "field1"));

        //列表
        List<Person> list = new ArrayList<>();
        Person lily=new Person(3, "lily");
        Person tom=new Person(5, "tom");
        Person sam=new Person(6, "sam");
        list.add(lily);
        list.add(tom);
        list.add(sam);
        jedis.lpush("list", JSON.toJSONString(list));
        System.out.println(jedis.lpop("list"));

        //追加
        jedis.append("hello","my world");

        //set
        Set<Person> set1=new HashSet<>();
        set1.add(lily);
        set1.add(tom);
        set1.add(sam);
        set1.add(sam);

        //集合可以有重复的键的，比如执行两次的jedis.sadd("set1", JSON.toJSONString(set1)); 则会有两个set1
        jedis.sadd("set1", JSON.toJSONString(set1));
        //jedis.scard("set1") 获得的是集合的数量，不是集合里元素的数量
        System.out.println(jedis.scard("set1"));
        System.out.println(jedis.smembers("set1"));
    }
}
