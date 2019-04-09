package com.pm.cms.service;

import com.pm.common.dto.SysResult;
import com.pm.cms.pojo.CmsFodder;

/**
 * 功能描述：
 * 【素材中心service】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/18 15:51
 */
public interface CmsFodderCentreService {

    /**
     * 新增素材
     *
     * @param cmsFodderDto dto
     * @return SysResult
     */
    SysResult insertFodder(CmsFodder cmsFodderDto);

    /**
     * 查找素材
     *
     * @param type       素材类型
     * @param fodderName 素材名
     * @return SysResult
     */
    SysResult selectFodder(Integer type, String fodderName);

    /**
     * 查询素材
     *
     * @param updateStatus 审核状态
     * @return SysResult
     */
    SysResult selectFodderByAuditStatus(Integer updateStatus);

    /**
     * 修改素材名
     *
     * @param fodderId    素材主键
     * @param fodderName  素材名称
     * @param auditStatus 素材状态
     * @return SysResult
     */
    SysResult updateFodder(Integer fodderId, String fodderName, Integer auditStatus);

    /**
     * 删除素材(逻辑删除)
     *
     * @param fodderIds 主键数组
     * @return SysResult
     */
    SysResult deleteFodder(Integer[] fodderIds);

    /**
     * 根据主键获取素材信息
     *
     * @param fodderId 素材主键
     * @return SysResult
     */
    SysResult selectFodderPathById(Integer fodderId);

}
