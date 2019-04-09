package com.pm.cms.chat.ws.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author huhaiqiang
 * @date 2018/10/06 11:15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CmsAdsSettingMsg {

    /**
     * 主键id
     */
    private Integer adsSetId;
    /**
     * 站牌唯一标识
     */
    private List<String> iccids;
    /**
     * 区域数量
     */
    private Integer subareaNum;
    /**
     * 声音开启区域
     */
    private Integer fodderVoiceArea;
}
