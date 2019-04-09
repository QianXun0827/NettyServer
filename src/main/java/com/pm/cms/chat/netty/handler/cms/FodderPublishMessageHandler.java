package com.pm.cms.chat.netty.handler.cms;

import com.pm.cms.chat.netty.pojo.CmsFodderMessage;
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
 * 富文本信息发布处理器
 *
 * @author huhaiqiang
 * @date 2018/10/06 10:46
 */
@Service("AdsMessageHandler")
@Scope("prototype")
@ChannelHandler.Sharable
@Slf4j
public class FodderPublishMessageHandler extends SimpleChannelInboundHandler<CmsFodderMessage> {

    @Autowired
    private CmsShelterFodderService cmsShelterFodderService;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CmsFodderMessage cmsAtfMessage) throws Exception {
        if (cmsAtfMessage.getStatus() == 1) {
            Integer updateState = 1;
            cmsShelterFodderService.updateShelterFodderState(cmsAtfMessage, updateState);
        } else {
            Integer updateState = 2;
            cmsShelterFodderService.updateShelterFodderState(cmsAtfMessage, updateState);
            WebSocketTransmitUtil.transmitHint(channelHandlerContext, "FailFodderInsert", "素材发布失败");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("rtfMessage publish exception...");
        ctx.close();
    }
}
