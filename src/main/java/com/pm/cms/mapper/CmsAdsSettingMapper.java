package com.pm.cms.mapper;

import com.pm.common.daoUtil.SimpleUpdateExtendedLanguageDriver;
import com.pm.common.service.PmMapper;
import com.pm.cms.pojo.CmsAdsSetting;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 功能描述：
 * 【广告屏配置mapper】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/01/03 15:56
 */
public interface CmsAdsSettingMapper extends PmMapper<CmsAdsSetting> {

    /**
     * 修改广告屏配置
     *
     * @param cmsAdsSetting CmsAdsSetting实体
     */
    @Update("UPDATE cms_ads_setting (#{cmsAdsSetting}) WHERE iccid = #{iccid}")
    @Lang(SimpleUpdateExtendedLanguageDriver.class)
    void updateCmsAdsSetting(CmsAdsSetting cmsAdsSetting);

    /**
     * 根据iccid查找站牌区域数量配置
     *
     * @param iccid 站牌标识
     * @return Integer
     */
    @Select("SELECT subarea_num FROM cms_ads_setting WHERE iccid = #{iccid}")
    Integer selectSubareaNumByIccid(String iccid);

    /**
     * 根据iccid查找有声音的区域
     *
     * @param iccid 站牌标识
     * @return String
     */
    @Select("SELECT fodder_area FROM cms_ads_setting WHERE iccid = #{iccid}")
    String selectVoiceAreaByIccid(String iccid);
}
