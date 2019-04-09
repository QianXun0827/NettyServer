package com.pm.cms.chat.ws.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author huhaiqiang
 * @date 2018/10/5/16:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CmsFodderDownMsg {

    private List<CmsFodderDownMsg> cmsFodderDownMsgList;

    /**
     * 站牌广告屏id
     */
    private Integer shelterFodderId;
    /**
     * 广告屏Id
     */
    private Integer fodderId;
    /**
     * 站牌唯一标识
     */
    private String iccid;
    /**
     * 所在区域
     */
    private Integer fodderArea;
    /**
     * 屏幕类型
     */
    private Integer shelterMonitor;
}
