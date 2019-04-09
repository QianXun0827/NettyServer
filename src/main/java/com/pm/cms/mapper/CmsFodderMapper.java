package com.pm.cms.mapper;

import com.pm.cms.common.provier.CmsSelectProvier;
import com.pm.cms.pojo.CmsBaiduAudit;
import com.pm.cms.pojo.CmsFodder;
import com.pm.common.daoUtil.SimpleInsertExtendedLanguageDriver;
import com.pm.common.daoUtil.SimpleSelectInExtendedLanguageDriver;
import com.pm.common.daoUtil.SimpleUpdateExtendedLanguageDriver;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 功能描述：
 * 【素材中心mapper】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/18 16:26
 */
public interface CmsFodderMapper {

    /**
     * 新增素材
     *
     * @param cmsFodder pojo
     */
    @Insert("INSERT INTO cms_fodder (#{cmsFodder})")
    @Lang(SimpleInsertExtendedLanguageDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "fodderId")
    void insertFodder(CmsFodder cmsFodder);

    /**
     * 查询素材
     *
     * @param type       素材类型
     * @param fodderName 素材名
     * @return List<CmsFodder>
     */
    @SelectProvider(type = CmsSelectProvier.class, method = "selectFodderByDto")
    List<CmsFodder> selectFodderByDto(@Param("type") Integer type, @Param("fodderName") String fodderName);

    /**
     * 删除素材(逻辑删除)
     *
     * @param fodderIds 素材id数组
     */
    @Update("UPDATE cms_fodder SET is_delete = 0,fodder_context = NULL WHERE fodder_id IN (#{fodderIds})")
    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    void deleteFodder(@Param("fodderIds") Integer[] fodderIds);

    /**
     * 根据主键获取素材信息
     *
     * @param fodderId 素材主键
     * @return String
     */
    @Select("SELECT fodder_id,fodder_name,fodder_type,fodder_context,file_size,file_time,file_name,file_format,audit_status,remark,is_delete,create_time,update_time FROM cms_fodder WHERE fodder_id = #{fodderId}")
    CmsFodder selectFodderInfoById(Integer fodderId);

    /**
     * 修改素材名
     *
     * @param fodderId    素材主键
     * @param fodderName  素材名
     * @param auditStatus 素材状态
     */
    @Update("UPDATE cms_fodder SET fodder_name = #{fodderName},audit_status = #{auditStatus} WHERE fodder_id = #{fodderId}")
    void updateFodder(@Param("fodderId") Integer fodderId, @Param("fodderName") String fodderName, @Param("auditStatus") Integer auditStatus);

    /**
     * 查询百度审核规则
     *
     * @return CmsBaiduAudit CmsBaiduAudit实体
     */
    @Select("SELECT audit_id,project_id,image_type,video_type,text_type FROM cms_baidu_audit")
    CmsBaiduAudit selectBaiduAuditInfo();

    /**
     * 修改素材
     *
     * @param cmsFodder CmsFodder实体
     */
    @Update("UPDATE cms_fodder (#{cmsFodder}) WHERE fodder_id = #{fodderId}")
    @Lang(SimpleUpdateExtendedLanguageDriver.class)
    void updateCmsFodder(CmsFodder cmsFodder);

    /**
     * 通过id数组查询素材是否被使用
     *
     * @param fodderIds 素材主键数组
     * @return int
     */
    @Select("SELECT COUNT(1) FROM cms_shelter_fodder WHERE fodder_id IN(#{fodderIds}) AND update_state IN(0,1,3)")
    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    int selectCountByFodderIds(@Param("fodderIds") Integer[] fodderIds);

    /**
     * 根据素材审核状态查询素材
     *
     * @param updateStatus 审核状态
     * @return List<CmsFodder>
     */
    @Select("SELECT fodder_id,fodder_name,fodder_type,fodder_context,file_size,file_time,file_name,file_format,audit_status,remark,is_delete,create_time,update_time FROM cms_fodder WHERE is_delete = 1 AND audit_status = #{updateStatus}")
    List<CmsFodder> selectFodderByAuditStatus(Integer updateStatus);
}
