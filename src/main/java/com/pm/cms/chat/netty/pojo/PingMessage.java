package com.pm.cms.chat.netty.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 心跳信息类
 *
 * @author liuxy
 */
@Getter
@Builder(toBuilder = true)
@ToString
public class PingMessage {

    private final String iccid;
    private final String token;

}
