package com.pm.cms.chat.netty.handler.cms;

import com.pm.cms.chat.netty.pojo.CmsFodderDownMessage;
import com.pm.cms.common.config.WebSocketTransmitUtil;
import com.pm.cms.service.CmsShelterFodderService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author huhaiqiang
 * @date 2018/10/5/17:45
 */
@Service("FodderDownMessageHandler")
@Scope("prototype")
@ChannelHandler.Sharable
@Slf4j
public class FodderDownMessageHandler extends SimpleChannelInboundHandler<CmsFodderDownMessage> {

    @Autowired
    private CmsShelterFodderService cmsShelterFodderService;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CmsFodderDownMessage cmsFodderDownMessage) throws Exception {
        if (cmsFodderDownMessage.getStatus() == 1) {
            Integer updateState = 4;
            cmsShelterFodderService.updateShelterFodderState(cmsFodderDownMessage, updateState);
        } else {
            Integer updateState = 5;
            cmsShelterFodderService.updateShelterFodderState(cmsFodderDownMessage, updateState);
            WebSocketTransmitUtil.transmitHint(channelHandlerContext, "FailFodderDown", "素材下架失败");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("rtfDownMessage publish exception...");
        ctx.close();
    }
}
