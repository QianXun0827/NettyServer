package com.pm.cms.chat.netty.handler.core;

import com.pm.cms.chat.netty.group.ChannelGroups;
import com.pm.cms.chat.netty.pojo.LoginMessage;
import com.pm.cms.chat.netty.session.Session;
import com.pm.cms.chat.netty.session.SessionManager;
import com.pm.cms.chat.util.LogoutUtil;
import com.pm.cms.chat.util.ResendMessage;
import com.pm.cms.common.util.RedisHelps;
import com.pm.cms.mapper.PmUserMapper;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 登录处理器
 *
 * @author liuxy
 */
@Service("loginHandler")
@Scope("prototype")
@Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginHandler.class);

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private RedisHelps redisHelps;
    @Autowired
    private LogoutUtil logoutUtil;
    @Autowired
    private ResendMessage resendMessage;
    @Autowired
    private PmUserMapper pmUserMapper;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginMessage msg) throws Exception {
        LOGGER.info("接收客户端登录信息：[{}]", msg);
        String iccid = msg.getIccid();
        String token = msg.getToken();
        LOGGER.info("获取到客户端iccid：[{}]", iccid);

        int state = sessionManager.clientLoginAuth(ctx, token, iccid);
        if (state == 1 || state == 2) {
            ChannelGroups.add(ctx.channel());
            logoutUtil.sendLogin(ctx, state);
            redisHelps.set(iccid, state + "");
            if (pmUserMapper.getUserCount(iccid) == 0) {
                resendMessage.resendFodderDownMsg(iccid, ctx);
                resendMessage.resendFodderPublishMsg(iccid, ctx);
            }
        } else {
            logoutUtil.sendLogin(ctx, state);
            logoutUtil.logout(ctx.channel());
            LOGGER.info("[{}]登陆出错:账号错误:{}", iccid, state);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("LoginHandler Exception：{}",cause);
        ctx.writeAndFlush("login exception");
        logoutUtil.logout(ctx.channel());
        Session session = SessionManager.get(ctx.channel());
        if (session != null) {
            SessionManager.remove(ctx.channel());
        }
        if (null != ctx) {
            ctx.close();
        }
    }
}


