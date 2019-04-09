package com.pm.cms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述：
 * 【广告屏配置pojo】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/01/03 11:38
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cms_ads_setting")
public class CmsAdsSetting implements Serializable {

    @Transient
    private static final long serialVersionUID = -6095517065911895937L;

    /**
     * 广告屏配置id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adsSetId;
    /**
     * 区域数量
     */
    private Integer subareaNum;
    /**
     * 站牌iccid
     */
    private String iccid;
    /**
     * 广告屏区域
     */
    private Integer fodderArea;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}