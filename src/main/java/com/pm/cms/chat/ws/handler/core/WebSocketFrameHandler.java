package com.pm.cms.chat.ws.handler.core;

import com.alibaba.fastjson.JSON;
import com.pm.cms.chat.netty.session.SessionManager;
import com.pm.cms.chat.util.LogoutUtil;
import com.pm.cms.chat.ws.group.ChannelGroups;
import com.pm.cms.chat.ws.pojo.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 信息处理类
 *
 * @author derekLiu
 */

@Service("webSocketFrameHandler")
@Scope("prototype")
@ChannelHandler.Sharable
@Slf4j
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private LogoutUtil logoutUtil;

    /**
     * 触发器
     * 注册成功之后，移除HttpRequestHandler
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            ctx.pipeline().remove(HttpRequestHandler.class);
            ChannelGroups.group.add(ctx.channel());
            ctx.writeAndFlush(new BinaryWebSocketFrame());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        WsMsg wsMsg = JSON.parseObject(msg.text(), WsMsg.class);
        log.info("接收到页面客户端[{}]信息：[{}]", ctx.channel().remoteAddress(), wsMsg);
        switch (wsMsg.getMsgType()) {
            case "loginMsg":
                LoginMsg loginMsg = JSON.parseObject(JSON.toJSONString(wsMsg.getLoginMsg()), LoginMsg.class);
                ctx.fireChannelRead(loginMsg);
                break;
            case "cmsAdsSetting":
                CmsAdsSettingMsg cmsAdsSettingMsg = JSON.parseObject(JSON.toJSONString(wsMsg.getCmsAdsSettingMsg()), CmsAdsSettingMsg.class);
                ctx.fireChannelRead(cmsAdsSettingMsg);
                break;
            case "cmsFodderPublish":
                CmsFodderPublishMsg cmsFodderPublishMsg = JSON.parseObject(JSON.toJSONString(wsMsg.getCmsFodderPublishMsg()), CmsFodderPublishMsg.class);
                ctx.fireChannelRead(cmsFodderPublishMsg);
                break;
            case "cmsFodderDown":
                CmsFodderDownMsg cmsFodderDownMsg = JSON.parseObject(JSON.toJSONString(wsMsg.getCmsFodderDownMsg()), CmsFodderDownMsg.class);
                ctx.fireChannelRead(cmsFodderDownMsg);
                break;
            default:
                break;
        }
    }

    /**
     * 设备移除下线
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if (SessionManager.get(ctx.channel()) != null) {
            log.info("用户登出:  " + SessionManager.get(ctx.channel()).getIccid());
            logoutUtil.logout(ctx.channel());
        } else {
            log.info("已登出");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("WebSocketFrameHandler Exception:", cause);
        ctx.close();
    }
}
