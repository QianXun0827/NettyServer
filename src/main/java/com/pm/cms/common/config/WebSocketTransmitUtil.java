package com.pm.cms.common.config;

import com.google.gson.Gson;
import com.pm.cms.chat.ws.pojo.WsMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 功能描述：
 * 【websocket传输消息提示工具类】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/01/03 17:08
 */
public class WebSocketTransmitUtil {

    /**
     *
     * @param channelHandlerContext 通道
     * @param hintType 提示类型
     * @param hintMsg 提示消息
     */
    public static void transmitHint(ChannelHandlerContext channelHandlerContext, String hintType, String hintMsg) {
        Gson gson = new Gson();
        WsMsg wsMsg = new WsMsg();
        wsMsg.setMsgType(hintType);
        wsMsg.setHintMsg(hintMsg);
        channelHandlerContext.writeAndFlush(new TextWebSocketFrame(gson.toJson(wsMsg)));
    }
}
