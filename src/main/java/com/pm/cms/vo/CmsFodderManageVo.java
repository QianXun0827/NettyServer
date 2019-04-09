package com.pm.cms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 功能描述：
 * 【站牌素材关系Vo】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/22 17:18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CmsFodderManageVo {

    private String iccid;
    private Integer shelterFodderId;
    private String shelterName;
    private String shelterDirection;
    private String fodderName;
    private String fodderContext;
    private Integer fodderId;
    private Integer fodderType;
    private Integer fodderArea;
    private Integer isVoice;
    private String updateState;
    private Integer fodderBeginTime;
    private Integer fodderEndTime;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
