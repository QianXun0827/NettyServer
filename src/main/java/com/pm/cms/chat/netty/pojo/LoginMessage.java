package com.pm.cms.chat.netty.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 登陆信息实体类
 *
 * @author liuxy
 */
@Getter
@Builder(toBuilder = true)
@ToString
public class LoginMessage {
    //用户id
    private String iccid;
    //登陆认证
    private String token;
    //登陆状态
    private int status;

}
