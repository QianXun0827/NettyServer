package com.pm.cms.chat.ws.handler.core;

import com.google.gson.Gson;
import com.pm.cms.chat.ws.pojo.LoginMsg;
import com.pm.cms.chat.ws.pojo.WsMsg;
import com.pm.cms.chat.util.WSUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @Auther: derekhehe@yahoo.com
 * @Date: Created in 2018/8/22 15:25
 * @Description: 登录handler
 * @Modified By:
 */
@Service("loginWsHandler")
@Scope("prototype")
@ChannelHandler.Sharable
@Slf4j
public class LoginWsHandler extends SimpleChannelInboundHandler<LoginMsg> {

    @Autowired
    private WSUtils wsUtils;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginMsg loginMsg) throws Exception {
        log.info("接收到页面客户端登录信息：[{}]", loginMsg);
        Gson gson = new Gson();
        boolean flag = wsUtils.loginAuth(loginMsg, channelHandlerContext);
        WsMsg wsMsg = new WsMsg();
        wsMsg.setMsgType("loginMsg");

        if (flag) {
            loginMsg.setState(1);
            wsMsg.setLoginMsg(loginMsg);
            channelHandlerContext.writeAndFlush(new TextWebSocketFrame(gson.toJson(wsMsg)));
        } else {
            loginMsg.setState(2);
            wsMsg.setLoginMsg(loginMsg);
            channelHandlerContext.writeAndFlush(new TextWebSocketFrame(gson.toJson(wsMsg)));
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("webSocket登陆错误：{}", cause);
    }
}
