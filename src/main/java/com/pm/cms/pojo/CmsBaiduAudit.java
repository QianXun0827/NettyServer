package com.pm.cms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述：
 * 【项目-百度审核类型校验pojo】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/01/22 11:47
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cms_baidu_audit")
public class CmsBaiduAudit implements Serializable {

    @Transient
    private static final long serialVersionUID = -6095517065911895957L;

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer auditId;
    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 图片审核类型
     */
    private Integer imageType;
    /**
     * 视频审核类型
     */
    private Integer videoType;
    /**
     * 文本审核类型
     */
    private Integer textType;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}
