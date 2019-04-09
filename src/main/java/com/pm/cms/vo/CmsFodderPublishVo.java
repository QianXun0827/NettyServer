package com.pm.cms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huhaiqiang
 * @date 2018/10/06 11:15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CmsFodderPublishVo {

    /**
     * 广告屏id
     */
    private Integer fodderId;
    /**
     * 广告屏区域
     */
    private String fodderName;
    /**
     * 发布类型
     * 0：轮播
     * 1：插播
     * 2：预播
     */
    private Integer publishType;
    /**
     * 广告屏区域
     */
    private Integer fodderArea;
    /**
     * 内容
     */
    private String fodderContent;
    /**
     * 内容格式
     */
    private String fodderFormat;
    /**
     * 是否开启声音
     */
    private Integer isVoice;
    /**
     * 站牌显示屏标识
     */
    private Integer shelterMonitor;
    /**
     * 开始时间
     */
    private Integer fodderBeginTime;
    /**
     * 结束时间
     */
    private Integer fodderEndTime;

}
