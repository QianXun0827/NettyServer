package com.pm.cms.chat.ws.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 页面传递消息总类
 *
 * @author Liu Xilin
 * @date Created in 2018/08/22 11:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsMsg {
    private String msgType;
    private LoginMsg loginMsg;
    private String hintMsg;
    private CmsFodderPublishMsg cmsFodderPublishMsg;
    private CmsFodderDownMsg cmsFodderDownMsg;
    private CmsAdsSettingMsg cmsAdsSettingMsg;

}
