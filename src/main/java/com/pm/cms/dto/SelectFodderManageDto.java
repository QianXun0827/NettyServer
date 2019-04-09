package com.pm.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述：
 * 【节目管理dto】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/22 14:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectFodderManageDto {
    private String iccid;
    private String fodderName;
    private Integer type;
    private Integer updateState;
}
