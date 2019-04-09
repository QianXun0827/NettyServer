package com.pm.cms.mapper;

import com.pm.cms.common.provier.CmsSelectProvier;
import com.pm.cms.dto.SelectFodderManageDto;
import com.pm.cms.vo.CmsFodderManageVo;
import com.pm.common.daoUtil.SimpleSelectInExtendedLanguageDriver;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 功能描述：
 * 【素材展示mapper】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/20 18:02
 */
public interface CmsFodderManageMapper {

    /**
     * 所有站牌分区设置回显
     *
     * @param selectFodderManageDto dto
     * @return List<CmsFodderManageVo>
     */
    @SelectProvider(type = CmsSelectProvier.class, method = "selectFodderByIccid")
    List<CmsFodderManageVo> selectFodderByIccid(@Param("selectFodderManageDto") SelectFodderManageDto selectFodderManageDto);

    /**
     * 删除中间表数据
     *
     * @param shelterFodderIds 站牌广告屏主键数组
     */
    @Delete("DELETE FROM cms_shelter_fodder WHERE shelter_fodder_id IN (#{shelterFodderIds})")
    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    void deleteShelterFodder(@Param("shelterFodderIds") Integer[] shelterFodderIds);

    /**
     * 通过主键数组查找数据是否正在使用
     *
     * @param shelterFodderIds 站牌素材主键数组
     * @return int
     */
    @Select("SELECT update_state FROM cms_shelter_fodder WHERE shelter_fodder_id IN(#{shelterFodderIds})")
    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    List<Integer> selectCountByShelterFodderIds(@Param("shelterFodderIds") Integer[] shelterFodderIds);
}
