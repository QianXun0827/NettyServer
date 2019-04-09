package com.pm.cms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author huhaiqiang
 * @date 2018/08/07 15:23
 * @apiNote 项目实体
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pm_project")
public class PmProject implements Serializable {

    @Transient
    private static final long serialVersionUID = -5739072296835354422L;
    /**
     * 项目Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 项目状态
     */
    private Integer projectStatus;
}
