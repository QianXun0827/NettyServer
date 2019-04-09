package com.pm.cms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author pengcheng
 * 该类是开发者信息，包含用户名和密码。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pm_user")
public class PmUser implements Serializable {
    @Transient
    private static final long serialVersionUID = 100L;
    @Id
    private Integer userId;

    private String userName;

    private String userPassword;

    private Integer parentId;

    private String userRole;

    private int userRoleType;
}