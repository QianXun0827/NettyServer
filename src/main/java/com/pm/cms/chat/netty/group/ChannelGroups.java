package com.pm.cms.chat.netty.group;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * ChannelGroup基本类
 *
 * @author liuxy
 */
public class ChannelGroups {

    /**
     * CHANNEL_GROUP保存所有链接进来的channel
     */
    private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup("ChannelGroups", GlobalEventExecutor.INSTANCE);

    /**
     * 添加channel
     *
     * @param channel
     */
    public static void add(Channel channel) {
        CHANNEL_GROUP.add(channel);
    }

    /**
     * 发送广播
     *
     * @param msg
     * @return
     */
    public static ChannelGroupFuture broadcast(Object msg) {
        return CHANNEL_GROUP.writeAndFlush(msg);
    }

    /**
     * 分组发送广播
     *
     * @param msg
     * @param matcher
     * @return
     */
    public static ChannelGroupFuture broadcast(Object msg, ChannelMatcher matcher) {
        return CHANNEL_GROUP.writeAndFlush(msg, matcher);
    }

    /**
     * 调用flush方法冲刷
     *
     * @return
     */
    public static ChannelGroup flush() {
        return CHANNEL_GROUP.flush();
    }

    /**
     * 移除channel
     *
     * @param channel
     * @return
     */
    public static boolean discard(Channel channel) {
        return CHANNEL_GROUP.remove(channel);
    }

    /**
     * 链接消失
     *
     * @return
     */
    public static ChannelGroupFuture disconnect() {
        return CHANNEL_GROUP.disconnect();
    }

    /**
     * 链接断开
     *
     * @param matcher
     * @return
     */
    public static ChannelGroupFuture disconnect(ChannelMatcher matcher) {
        return CHANNEL_GROUP.disconnect(matcher);
    }

    /**
     * 判断是否已经包含
     *
     * @param channel
     * @return
     */
    public static boolean contains(Channel channel) {
        return CHANNEL_GROUP.contains(channel);
    }

    /**
     * channel多少
     *
     * @return
     */
    public static int size() {
        return CHANNEL_GROUP.size();
    }
}