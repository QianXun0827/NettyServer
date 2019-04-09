package com.pm.cms.service;

import com.pm.cms.pojo.CmsFodder;

import java.util.List;

/**
 * 功能描述：
 * 【视频审核成功回调Service】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/01/25 18:00
 */
public interface CmsFodderNoticeService {

    /**
     * 查找所有审核中的ads视频
     *
     * @return list
     */
    List<CmsFodder> selectFodderInReviewState();

    /**
     * 处理审核结果
     *
     * @param cmsShelterFodder 站牌素材实体
     */
    void disposeFodderVideoAuditResult(CmsFodder cmsShelterFodder);

    /**
     * 文本审核，同步结果
     *  @param context  文本内容
     * @param textType 模版编号
     */
    void checkTextInfo(CmsFodder context, Integer textType);
}
