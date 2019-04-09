package com.pm.cms.chat.ws.handler.cms;

import com.google.gson.Gson;
import com.pm.cms.chat.util.PushNotifications;
import com.pm.cms.chat.ws.pojo.CmsAdsSettingMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author huhaiqiang
 * @date 2018/10/06 11:14
 */
@Service("CmsAdsSettingHandler")
@Scope("prototype")
@ChannelHandler.Sharable
public class CmsAdsSettingHandler extends SimpleChannelInboundHandler<CmsAdsSettingMsg> {

    @Autowired
    private PushNotifications pushNotifications;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CmsAdsSettingMsg cmsAdsSettingMsg) throws Exception {
        pushNotifications.sendCmsAdsSetting(cmsAdsSettingMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Gson gson = new Gson();
        ctx.writeAndFlush(new TextWebSocketFrame(gson.toJson("广告屏launcher设置失败")));
        ctx.close();
    }
}
