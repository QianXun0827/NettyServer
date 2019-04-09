package com.pm.cms.service;

/**
 * 功能描述：
 * 【核心Service】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/22 11:52
 */
public interface CoreService {

    /**
     * 判断站牌中是否存在iccid
     *
     * @param iccid 站牌标识
     * @return int
     */
    int getAuth(String iccid);
}
