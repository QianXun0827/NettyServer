package com.pm.cms.chat.netty.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author huhaiqiang
 * @date 2018/10/5/17:50
 */
@Getter
@Builder(toBuilder = true)
@ToString
public class CmsFodderDownMessage {

    private Integer fodderId;
    private Integer shelterFodderId;
    private Integer shelterMonitor;
    private Integer fodderArea;
    private String iccid;
    private Integer status;
}
