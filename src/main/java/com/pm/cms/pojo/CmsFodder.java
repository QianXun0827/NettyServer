package com.pm.cms.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述：
 * 【素材表pojo类】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/18 15:25
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cms_fodder")
public class CmsFodder implements Serializable {

    @Transient
    private static final long serialVersionUID = -8437737470926257125L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fodderId;
    /**
     * 素材名称
     */
    private String fodderName;
    /**
     * 类型,1=图片,2=视频,3=文字,4=直播
     */
    private Integer fodderType;
    /**
     * 文件内容，文件路径&文字
     */
    private String fodderContext;
    /**
     * 文件大小
     */
    private Integer fileSize;
    /**
     * 持续时间
     */
    private String fileTime;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件格式
     */
    private String fileFormat;
    /**
     * 审核状态,0=审核中1=审核成功2=审核失败
     */
    private Integer auditStatus;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否删除0=已删除1=未删除
     */
    private Integer isDelete;
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
}