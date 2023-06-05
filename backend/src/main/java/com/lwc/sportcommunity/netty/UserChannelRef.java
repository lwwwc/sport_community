package com.lwc.sportcommunity.netty;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * Create by LWC on 2023/4/29 17:14
 */
public class UserChannelRef {
    private static HashMap<Integer, Channel> manager = new HashMap<>();

    public static void put(Integer senderId, Channel channel) {
        manager.put(senderId, channel);
    }

    public static Channel get(Integer senderId) {
        return manager.get(senderId);
    }

    public static void output() {
        for (HashMap.Entry<Integer, Channel> entry : manager.entrySet()) {
            System.out.println("UserId: " + entry.getKey()
                    + ", ChannelId: " + entry.getValue().id().asLongText());
        }
    }
}
