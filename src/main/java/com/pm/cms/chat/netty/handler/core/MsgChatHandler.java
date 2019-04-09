package com.pm.cms.chat.netty.handler.core;

import com.pm.cms.chat.netty.pojo.*;
import com.pm.cms.chat.netty.session.Session;
import com.pm.cms.chat.netty.session.SessionManager;
import com.pm.cms.chat.util.LogoutUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 消息分发器
 *
 * @author liuxy
 */
@Service("msgChatHandler")
@Scope("prototype")
@Sharable
public class MsgChatHandler extends SimpleChannelInboundHandler<Msg.Message> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgChatHandler.class);

    @Autowired
    private LogoutUtil logoutUtil;

    @Override
    protected void channelRead0(ChannelHandlerContext chx, Msg.Message message) {
        LOGGER.info("客户端[{}]连接成功", chx.channel().remoteAddress());

        //通过消息类型进行消息的分发
        switch (message.getMessageType()) {
            //心跳包客户端发往服务器端
            case HEAT_BEAT_CLIENT:
                if (message.getPingMessage() != null) {
                    Msg.PingMessage ping = message.getPingMessage();
                    PingMessage pingMessage = PingMessage.builder()
                            .iccid(ping.getIccid())
                            .token(ping.getToken())
                            .build();
                    chx.fireChannelRead(pingMessage);
                } else {
                    LOGGER.info("heat_beat_client==null");
                }
                break;
            //客户端登陆
            case CLIENT_LOGIN:
                if (message.getLoginMessage() != null) {
                    Msg.LoginMessage login = message.getLoginMessage();
                    LoginMessage msg = LoginMessage.builder()
                            .iccid(login.getIccid())
                            .token(login.getToken())
                            .build();
                    chx.fireChannelRead(msg);
                    LOGGER.info("用户[{}]登陆", chx.channel().remoteAddress());
                } else {
                    LOGGER.info("loginMessage == null");
                }
                break;
            case ADS_SETTING:
                if (message.getAdsSettingMessage() != null) {
                    Msg.AdsSettingMessage adsSettingMessage = message.getAdsSettingMessage();
                    CmsFodderSettingMessage msg = CmsFodderSettingMessage.builder()
                            .iccid(adsSettingMessage.getIccid())
                            .subareaNum(adsSettingMessage.getSubareaNum())
                            .fodderVoiceArea(adsSettingMessage.getFodderAreaVoice())
                            .status(adsSettingMessage.getStatus())
                            .build();
                    chx.fireChannelRead(msg);
                } else {
                    LOGGER.info("adsSettingMessage == null");
                }
                break;
            case FODDER_PUBLISH:
                if (message.getFodderPublishMessage() != null) {
                    Msg.FodderPublishMessage adsMessage = message.getFodderPublishMessage();
                    CmsFodderMessage msg = CmsFodderMessage.builder()
                            .iccid(adsMessage.getIccid())
                            .fodderId(adsMessage.getFodderId())
                            .fodderArea(adsMessage.getFodderArea())
                            .shelterMonitor(adsMessage.getShelterMonitor())
                            .status(adsMessage.getStatus())
                            .build();
                    chx.fireChannelRead(msg);
                } else {
                    LOGGER.info("adsPublishMessage == null");
                }
                break;
            case FODDER_DOWN:
                if (message.getFodderDownMessage() != null) {
                    Msg.FodderDownMessage adsDownMessage = message.getFodderDownMessage();
                    CmsFodderDownMessage msg = CmsFodderDownMessage.builder()
                            .iccid(adsDownMessage.getIccid())
                            .fodderId(adsDownMessage.getFodderId())
                            .fodderArea(adsDownMessage.getFodderArea())
                            .shelterMonitor(adsDownMessage.getShelterMonitor())
                            .status(adsDownMessage.getStatus())
                            .build();
                    chx.fireChannelRead(msg);
                } else {
                    LOGGER.info("adsDownMessage == null");
                }
                break;
            default:
                break;
        }
        ReferenceCountUtil.release(message);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }

    /**
     * 设备移除下线
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if (SessionManager.get(ctx.channel()) != null) {
            LOGGER.info("用户登出:  " + SessionManager.get(ctx.channel()).getIccid());
            logoutUtil.logout(ctx.channel());
        } else {
            LOGGER.info("已登出");
        }
    }

    /**
     * 设备上线，判断是否同一台机上是否登陆
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        LOGGER.error("发生异常,用户将下线，session 将被移除，channel 将被关闭！" + cause.getMessage());
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
