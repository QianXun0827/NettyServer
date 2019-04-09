package com.pm.cms.chat.netty.pojo;

import com.pm.cms.chat.netty.pojo.Msg.MessageType;
import lombok.Builder;
import lombok.Getter;

/**
 * 消息总类
 *
 * @author LIU XILIN
 */
@Builder(toBuilder = true)
@Getter
public class Message {
    private MessageType messageType;
    private LoginMessage loginMessage;
    private PingMessage pingMessage;
}
