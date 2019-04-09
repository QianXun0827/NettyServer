package com.pm.cms.service;

import com.pm.cms.chat.netty.pojo.CmsFodderDownMessage;
import com.pm.cms.chat.netty.pojo.CmsFodderSettingMessage;
import com.pm.common.dto.SysResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 功能描述：
 * 【】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/01/03 17:13
 */
public interface CmsAdsService {

    /**
     * 文件下载路径获取
     *
     * @param adsId
     * @return
     */
    SysResult getAdsById(Integer adsId);

    /**
     * 修改广告屏launcher配置
     *
     * @param cmsAdsSettingMsg
     * @param projectId
     * @return
     */
    SysResult updateCmsAdsSetting(CmsFodderSettingMessage cmsAdsSettingMsg, Integer projectId);

    /**
     * 下架广告屏内容
     *
     * @param cmsFodderDownMessage
     * @param projectId
     * @return
     */
    SysResult cmsAdsDownInfo(CmsFodderDownMessage cmsFodderDownMessage, Integer projectId);


    /**
     * 项目下所有站牌回显
     */
    SysResult getInfoAds(Integer projectId, String iccid);

    /**
     * 审核
     *
     * @param file
     * @param suffix
     * @param iccid
     * @return
     */
    SysResult contentAudit(MultipartFile file, String suffix, String iccid);
}
