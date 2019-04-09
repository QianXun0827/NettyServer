package com.pm.cms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能描述：
 * 【】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/01/04 14:37
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FodderShelterGenreVo implements Serializable {

    private String iccid;
    private Integer type;
    private Integer fodderId;
    private Integer fodderArea;
    private Integer shelterMonitor;
}
