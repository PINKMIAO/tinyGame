package com.baven;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class BroadCaster {

    private final static ChannelGroup _channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private BroadCaster(){
    }

    public static void addChannel(Channel channel) {
        _channelGroup.add(channel);
    }

    public static void removeChannel(Channel channel) {
        _channelGroup.remove(channel);
    }

    public static void broadcast(Object msg) {
        _channelGroup.writeAndFlush(msg);
    }

}
