package com.pm.cms.service;

import com.pm.common.dto.SysResult;
import com.pm.cms.dto.SelectFodderManageDto;

/**
 * 功能描述：
 * 【素材展示Service】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/20 18:01
 */
public interface CmsFodderManageService {

    /**
     * 所有站牌分区设置回显
     *
     * @param selectFodderManageDto SelectFodderManageDto实体
     * @return SysResult
     */
    SysResult selectFodderManage(SelectFodderManageDto selectFodderManageDto);

    /**
     * 删除站牌素材关系
     *
     * @param fodderIds 素材主键数组
     * @return SysResult
     */
    SysResult deleteShelterFodder(Integer[] fodderIds);
}
