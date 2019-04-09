package com.pm.cms.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 站牌选择Vo类
 *
 * @author huhaiqiang
 * @date 2018/08/25 14:15
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelterVo {

    private String iccid;

    private String shelterName;

    private String shelterDirection;
}
