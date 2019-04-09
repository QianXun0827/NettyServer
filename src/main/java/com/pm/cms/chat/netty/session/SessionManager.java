package com.pm.cms.chat.netty.session;

import com.pm.cms.chat.util.LogoutUtil;
import com.pm.cms.service.CoreService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session管理类
 *
 * @author liuxy
 */
@Service("sessionManager")
@Slf4j
public class SessionManager {
    @Autowired
    private CoreService coreService;

    @Autowired
    private LogoutUtil logoutUtil;
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();
    private static Map<Session, String> sessionKeyMap = new ConcurrentHashMap<Session, String>();

    private static Map<Channel, String> channelHandlerContextMap = new ConcurrentHashMap<Channel, String>();
    private static Map<String, Channel> channelHandlerContextKeyMap = new ConcurrentHashMap<String, Channel>();

    /**
     * 通过clientId进行存储Session
     *
     * @param iccid
     * @param session
     */
    public static void add(String iccid, Session session) {
        sessionMap.put(iccid, session);
        sessionKeyMap.put(session, iccid);
        channelHandlerContextMap.put(session.getChannel(), iccid);
        channelHandlerContextKeyMap.put(iccid, session.getChannel());
    }

    /**
     * 通过clientId获取Session
     *
     * @param iccid
     * @return
     */
    public static Session get(String iccid) {
        try {
            Session session = sessionMap.get(iccid);
            return session;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 用户登录认证
     *
     * @param ctx
     * @param token
     * @param iccid
     * @return
     */
    public int clientLoginAuth(ChannelHandlerContext ctx, String token, String iccid) {
        Session session = SessionManager.get(iccid);

        int iccidCount = coreService.getAuth(iccid);
        switch (iccidCount) {
            case 1:
                if (session == null) {
                    session = new Session(ctx.channel(), iccid, token);
                    // 添加进本地，并保存到服务器
                    // 采用用户id作为key方便查找
                    SessionManager.add(iccid, session);
                    return 1;
                } else if (session != null && session.getIccid().equals(iccid)) {
                    logoutUtil.LogoutMsg(session);
                    logoutUtil.logout(session.getChannel());
                    session = new Session(ctx.channel(), iccid, token);
                    SessionManager.add(iccid, session);
                    return 2;
                } else if (session != null && !session.getIccid().equals(iccid)) {
                    logoutUtil.LogoutMsg(session);
                    logoutUtil.logout(session.getChannel());
                    session = new Session(ctx.channel(), iccid, token);
                    SessionManager.add(iccid, session);
                    return 1;
                }
            case 0:
                return 3;
            default:
                break;
        }
        return 0;

    }

    /**
     * 通过session移除信息
     *
     * @param session
     */
    public static void remove(Session session) {
        String key = sessionKeyMap.get(session);
        // 防止重复操作
        if (key == null) {
            return;
        }
        Channel channel = channelHandlerContextKeyMap.get(key);
        sessionMap.remove(key);
        sessionKeyMap.remove(session);
        channelHandlerContextMap.remove(channel);
        channelHandlerContextKeyMap.remove(key);
    }

    /**
     * 通过Channel移除信息
     *
     * @param channel
     */
    public static void remove(Channel channel) {
        String key = channelHandlerContextMap.get(channel);
        // 防止重复操作
        if (key == null) {
            return;
        }
        Session session = sessionMap.get(key);
        sessionMap.remove(key);
        sessionKeyMap.remove(session);
        channelHandlerContextMap.remove(channel);
        channelHandlerContextKeyMap.remove(key);
    }

    /**
     * 通过channel获取session
     *
     * @param channel
     * @return
     */
    public static Session get(Channel channel) {
        try {
            String key = channelHandlerContextMap.get(channel);
            if (key == null) {
                return null;
            }
            return sessionMap.get(key);
        } catch (Exception e) {
            return null;
        }
    }
}
