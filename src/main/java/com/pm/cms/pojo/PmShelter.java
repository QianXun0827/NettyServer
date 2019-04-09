package com.pm.cms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author huhaiqiang
 * @date 2018/08/07/ 11:04
 * @apiNote 站牌实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pm_shelter")
public class PmShelter implements Serializable {

    @Transient
    private static final long serialVersionUID = 6462914265858880921L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**站牌ID*/
    private Integer shelterId;
    /**
     * 站牌名
     */
    private String shelterName;
    /**
     * 站牌所在地
     */
    private String shelterLocation;
    /**
     * 站牌方向
     */
    private String shelterDirection;
    /**
     * 所属站点
     */
    private String stationName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 经度
     */
    private String shelterLongitude;
    /**
     * 纬度
     */
    private String shelterLatitude;
    /**
     * 站牌状态
     */
    private Integer shelterStatus;
    /**
     * 站牌版本
     */
    private Integer shelterVersion;
    /**
     * 站牌标识ID
     */
    private String iccid;
    /**
     * 站牌所在区域
     */
    private String shelterRegion;
    /**
     * 站牌类型
     */
    private Integer shelterType;


}
