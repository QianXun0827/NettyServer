package com.pm.cms.chat.netty.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author huhaiqiang
 * @date 2018/10/06 14:32
 */
@Getter
@Builder(toBuilder = true)
@ToString
public class CmsFodderSettingMessage {

    private Integer adsSetId;
    private String iccid;
    private Integer subareaNum;
    private Integer fodderVoiceArea;
    private Integer status;
}
