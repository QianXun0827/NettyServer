package com.pm.cms.service;

import com.pm.cms.chat.netty.pojo.CmsFodderSettingMessage;
import com.pm.common.dto.SysResult;
import com.pm.cms.chat.ws.pojo.CmsFodderPublishMsg;

/**
 * 功能描述：
 * 【站牌素材Service】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/20 14:19
 */
public interface CmsShelterFodderService {

    /**
     * 素材发布
     *
     * @param cmsFodderPublishMsg CmsFodderMsg实体
     * @return SysResult
     */
    SysResult insertCmsShelterFodder(CmsFodderPublishMsg cmsFodderPublishMsg);

    /**
     * 修改发布、下架状态
     *
     * @param object      带有一系列特殊参数的实体
     * @param updateState 状态
     * @return SysResult
     */
    SysResult updateShelterFodderState(Object object, Integer updateState);

    /**
     * 配置更改
     *
     * @param cmsFodderSettingMessage CmsAdsSettingMessage实体
     */
    void updateCmsFodderSetting(CmsFodderSettingMessage cmsFodderSettingMessage);
}
