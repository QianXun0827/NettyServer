package com.pm.cms.chat.netty.session;


import io.netty.channel.Channel;

/**
 * Session对象类
 * @author liuxy
 *
 */
public class Session {

    private Channel channel;

    /**
     * 用户id
     */
    private String iccid;
    /**
     * 登录授权码
     */
    private String token;
    /**
     * 生成服务端对服务端的session
     *
     * @param channel
     */
    public Session(Channel channel) {
        this.channel = channel;
    }

    /**
     * 生成服务端对客户端session
     *
     * @param channel
     * @param iccid
     */
    public Session(Channel channel, String iccid) {
        this.channel = channel;
        this.iccid = iccid;
    }

    /**
     * 生成服务端对客户端session
     *
     * @param channel
     * @param iccid
     * @param token
     */
    public Session(Channel channel, String iccid,String token) {
        this.channel = channel;
        this.iccid = iccid;
        this.token = token;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getIccid() {
        return iccid;
    }
    public String getToken() {
        return token;
    }

}
