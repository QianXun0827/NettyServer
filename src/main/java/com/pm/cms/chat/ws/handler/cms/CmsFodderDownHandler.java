package com.pm.cms.chat.ws.handler.cms;

import com.google.gson.Gson;
import com.pm.cms.chat.util.PushNotifications;
import com.pm.common.dto.SysResult;
import com.pm.cms.chat.ws.pojo.CmsFodderDownMsg;
import com.pm.cms.service.CmsShelterFodderService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huhaiqiangf
 * @date 2018/10/5/16:36
 */
@Service("CmsAdsDownHandler")
@Scope("prototype")
@ChannelHandler.Sharable
public class CmsFodderDownHandler extends SimpleChannelInboundHandler<CmsFodderDownMsg> {

    @Autowired
    private CmsShelterFodderService cmsShelterFodderService;

    @Autowired
    private PushNotifications pushNotifications;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CmsFodderDownMsg cmsFodderDownMsg) throws Exception {
        List<CmsFodderDownMsg> cmsFodderDownMsgList = cmsFodderDownMsg.getCmsFodderDownMsgList();
        for (CmsFodderDownMsg fodderDownMsg : cmsFodderDownMsgList) {
            Integer updateState = 3;
            SysResult sysResult = cmsShelterFodderService.updateShelterFodderState(fodderDownMsg, updateState);
            if (sysResult.isOk()) {
                pushNotifications.sendCmsAdsDown(fodderDownMsg);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Gson gson = new Gson();
        ctx.writeAndFlush(new TextWebSocketFrame(gson.toJson("富文本发布失败")));
        ctx.close();
    }
}
