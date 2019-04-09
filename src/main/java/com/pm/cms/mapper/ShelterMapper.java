package com.pm.cms.mapper;

import com.pm.cms.pojo.PmShelter;
import com.pm.cms.common.provier.CmsSelectProvier;
import com.pm.common.service.PmMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 站牌Mapper层
 *
 * @author huhaiqiang
 * @date 2018/08/07 12:54
 */
public interface ShelterMapper extends PmMapper<PmShelter> {

    /**
     * 选择站牌
     *
     * @param subareaNum 区域编号
     * @return List
     */
    @SelectProvider(type = CmsSelectProvier.class, method = "selectShelterInfo")
    List<PmShelter> selectShelterInfo(@Param("subareaNum") Integer subareaNum);
}
