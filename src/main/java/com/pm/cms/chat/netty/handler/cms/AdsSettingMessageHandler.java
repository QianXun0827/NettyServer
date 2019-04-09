package com.pm.cms.chat.netty.handler.cms;

import com.pm.cms.chat.netty.pojo.CmsFodderSettingMessage;
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
@Service("AdsSettingMessageHandler")
@Scope("prototype")
@ChannelHandler.Sharable
@Slf4j
public class AdsSettingMessageHandler extends SimpleChannelInboundHandler<CmsFodderSettingMessage> {

    @Autowired
    private CmsShelterFodderService cmsShelterFodderService;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CmsFodderSettingMessage cmsFodderSettingMessage) throws Exception {
        if (cmsFodderSettingMessage.getStatus() == 1) {
            cmsShelterFodderService.updateCmsFodderSetting(cmsFodderSettingMessage);
        } else {
            WebSocketTransmitUtil.transmitHint(channelHandlerContext, "FailAdsSetting", "广告屏launcher设置失败");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("rtfMessage publish exception...");
        ctx.close();
    }
}
