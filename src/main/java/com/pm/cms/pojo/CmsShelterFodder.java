package com.pm.cms.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 功能描述：
 * 【站牌广告屏pojo】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/01/03 10:53
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cms_shelter_fodder")
public class CmsShelterFodder implements Serializable {

    @Transient
    private static final long serialVersionUID = -7552690365865573223L;

    /**
     * 站牌广告屏id
     */
    @Id
    private Integer shelterFodderId;
    /**
     * 广告屏id
     */
    private Integer fodderId;
    /**
     * 站牌唯一标识
     */
    private String iccid;
    /**
     * 发布类型
     */
    private Integer publishType;
    /**
     * 广告屏区域
     */
    private Integer fodderArea;
    /**
     * 声音开关
     * 0：声音关闭
     * 1：声音开启
     */
    private Integer isVoice;
    /**
     * 显示器类型
     * 1：LCD显示器
     * 2：LED显示器
     * 3：透明屏
     * 4：广告屏
     */
    private Integer shelterMonitor;
    /**
     * 预播开始时间
     */
    private Integer fodderBeginTime;
    /**
     * 预播结束时间
     */
    private Integer fodderEndTime;
    /**
     * 更新状态
     * 0：发布中
     * 1：发布成功
     * 2：发布失败
     * 3：下架中
     * 4：下架失败
     */
    private Integer updateState;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    /**
     * 站牌唯一标识
     */
    @Transient
    private List<String> iccids;
}