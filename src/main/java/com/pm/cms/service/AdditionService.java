package com.pm.cms.service;

import com.pm.common.dto.SysResult;

/**
 * 站牌选择Service层
 *
 * @author huhaiqiang
 * @date 2018/08/07
 */
public interface AdditionService {

    /**
     * 广告机发布时根据区域数量选择的站牌列表
     *
     * @param subareaNum
     * @return
     */
    SysResult chooseShelter(Integer subareaNum);
}
