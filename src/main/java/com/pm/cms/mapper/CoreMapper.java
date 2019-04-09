package com.pm.cms.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * 功能描述：
 * 【核心mapper】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/22 13:56
 */
public interface CoreMapper {

    /**
     * 判断站牌中是否存在iccid
     *
     * @param iccid 站牌标识
     * @return int
     */
    @Select("select count(1) from pm_shelter where iccid = #{iccid}")
    int getAuth(String iccid);
}
