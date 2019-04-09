package com.pm.cms.chat.util;


import com.pm.cms.chat.netty.group.ChannelGroups;
import com.pm.cms.chat.netty.pojo.Msg;
import com.pm.cms.chat.netty.session.Session;
import com.pm.cms.chat.netty.session.SessionManager;
import com.pm.cms.common.util.RedisHelps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登出工具类
 *
 * @author Liuxy
 */
@Service
public class LogoutUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutUtil.class);
    @Autowired
    private RedisHelps redisHelps;
    /**
     * 关闭channel
     */
    public void logout(Channel channel) {
        Session session = SessionManager.get(channel);
        if (session != null) {
            String token = session.getToken();
            String userId = session.getIccid();
            SessionManager.remove(channel);
            ChannelGroups.discard(channel);
            channel.close();
            redisHelps.delete(userId);
            LOGGER.info("用户[{}]下线", userId);
        } else {
            channel.close();
            LOGGER.info("session为空,channel关闭");
        }
    }

    public void LogoutMsg(Session session) {
        Msg.LoginMessage.Builder loginMessage = Msg.LoginMessage.newBuilder();
        Msg.Message.Builder message = Msg.Message.newBuilder();
        loginMessage.setStatus(4);
        message.setMessageType(Msg.MessageType.CLIENT_LOGIN);
        message.setLoginMessage(loginMessage.build());
        session.getChannel().writeAndFlush(message.build());
    }

    public void sendLogin(ChannelHandlerContext ctx, int state) {
        Msg.Message.Builder message = Msg.Message.newBuilder();
        Msg.LoginMessage.Builder login = Msg.LoginMessage.newBuilder();
        login.setStatus(state);
        message.setLoginMessage(login.build());
        message.setMessageType(Msg.MessageType.CLIENT_LOGIN);
        ctx.writeAndFlush(message.build());
    }

}
