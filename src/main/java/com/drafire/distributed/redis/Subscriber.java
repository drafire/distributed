package com.drafire.distributed.redis;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println(String.format("subscribe redis channel success, channel %s, subscribedChannels %d",
                channel, subscribedChannels));
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println(String.format("unsubscribe redis channel, channel %s, subscribedChannels %d",
                channel, subscribedChannels));
    }

    @Override
    public void onMessage(String channel, String message) {
        System.out.println(String.format("从%s收到redis消息%s", channel, message));
    }
}
