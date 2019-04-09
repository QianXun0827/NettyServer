package com.pm.cms.chat.netty.handler.core;


import com.pm.cms.chat.netty.pojo.PingMessage;
import com.pm.cms.chat.util.LogoutUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 心跳处理器
 *
 * @author liuxy
 */
@Service("pingHandler")
@Scope("prototype")
@Sharable
public class PingHandler extends SimpleChannelInboundHandler<PingMessage> {
    private static final Logger LOGGER = Logger.getLogger(PingHandler.class);

    @Autowired
    private LogoutUtil logoutUtil;
    private int loss_connect_time = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PingMessage msg) throws Exception {
        //获取心跳内容
        LOGGER.info("收到心跳信息:" + msg.getIccid());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    /**
     * 自定义事件触发器，通过定时监控读写状态进行心跳检测
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                loss_connect_time++;
                LOGGER.info("40秒没有接收到客户端的信息了");
                if (loss_connect_time > 3) {
                    logoutUtil.logout(ctx.channel());
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        LOGGER.error("Ping exception");
//      ctx.writeAndFlush("ping exception");
        ctx.close();
    }

}  
