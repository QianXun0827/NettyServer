package com.pm.cms.mapper;

import com.pm.cms.pojo.CmsShelterFodder;
import com.pm.cms.common.provier.CmsInsertProvier;
import com.pm.cms.common.provier.CmsSelectProvier;
import com.pm.cms.vo.CmsFodderPublishVo;
import com.pm.cms.vo.FodderShelterGenreVo;
import com.pm.common.daoUtil.SimpleSelectInExtendedLanguageDriver;
import com.pm.common.daoUtil.SimpleUpdateExtendedLanguageDriver;
import com.pm.common.service.PmMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 功能描述：
 * 【站牌素材mapper】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/01/03 18:05
 */
public interface CmsShelterFodderMapper extends PmMapper<CmsShelterFodder> {

    /**
     * 站牌广告屏关系数据插入
     *
     * @param cmsShelterFodder 实体
     * @return int
     */
    @InsertProvider(type = CmsInsertProvier.class, method = "insertShelterFodderMiddle")
    int insertShelterFodder(@Param("cmsShelterFodder") CmsShelterFodder cmsShelterFodder);

    /**
     * 新增时查询站牌正在播放的内容类型
     *
     * @param iccidList iccid集合
     * @return list
     */
    @SelectProvider(type = CmsSelectProvier.class, method = "selectPublishTypeByIccid")
    List<FodderShelterGenreVo> selectPublishTypeByIccid(@Param("list") List<String> iccidList);

    /**
     * 根据iccid查找未更新状态的发布内容
     *
     * @param iccid 站牌标识
     * @return list
     */
    @Select("SELECT cf.fodder_id,cf.fodder_name,cf.fodder_context,cf.file_format,csf.is_voice,csf.shelter_monitor,csf.fodder_area,csf.iccid,csf.publish_type,csf.fodder_begin_time,csf.fodder_end_time FROM cms_fodder cf " +
            "INNER JOIN cms_shelter_fodder csf ON cf.fodder_id = csf.fodder_id " +
            "WHERE csf.iccid = #{iccid} AND csf.update_state = '0'")
    List<CmsFodderPublishVo> selectUpdateState(String iccid);

    /**
     * 根据iccid查找未更新状态的下架内容
     *
     * @param iccid
     * @return
     */
    @Select("SELECT fodder_id,is_voice,shelter_monitor,fodder_area,iccid " +
            "FROM cms_shelter_fodder " +
            "WHERE iccid = #{iccid} AND update_state = '3'")
    List<CmsShelterFodder> selectDownUpdateState(String iccid);

    /**
     * 修改关系表状态
     *
     * @param cmsShelterFodder CmsShelterFodder实体
     */
    @Update("UPDATE cms_shelter_fodder (#{cmsShelterFodder}) WHERE iccid = #{iccid} AND fodder_id = #{fodderId} AND fodder_area = #{fodderArea}")
    @Lang(SimpleUpdateExtendedLanguageDriver.class)
    void updateCmsShelterFodder(CmsShelterFodder cmsShelterFodder);

    /**
     * 重置站牌内所有区域声音开关
     *
     * @param iccid 站牌标识
     */
    @Update("UPDATE cms_shelter_fodder SET is_voice = 0 WHERE iccid = #{iccid}")
    void resetShelterVoice(String iccid);

    /**
     * 选择站牌内声音开启的区域
     *
     * @param iccid           站牌标识
     * @param fodderVoiceArea 声音开启区域
     * @return int
     */
    @Update("UPDATE cms_shelter_fodder SET is_voice = 1 WHERE iccid = #{iccid} AND fodder_area = #{fodderVoiceArea}")
    void updateIsVoice(@Param("iccid") String iccid, @Param("fodderVoiceArea") Integer fodderVoiceArea);

    /**
     * 根据iccid查找fodderId
     *
     * @param iccid 唯一id
     * @return Integer[]
     */
    @Select("SELECT fodder_id FROM cms_shelter_fodder WHERE iccid = #{iccid}")
    Integer[] selectFodderInfoByIccid(String iccid);

    /**
     * 修改站牌素材状态为下架成功
     *  @param fodderIds 素材主键数组
     * @param iccid     站牌标识
     * @param updateState
     */
    @Update("UPDATE cms_shelter_fodder SET update_state = #{updateState} WHERE fodder_id IN (#{fodderId}) AND iccid = #{iccid}")
    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    void updateFodderStatusByIccid(@Param("fodderIds") Integer[] fodderIds, @Param("iccid") String iccid, @Param("updateState") Integer updateState);
}
