package com.pm.cms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.DynaBean;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.pm.cms.chat.netty.pojo.CmsFodderSettingMessage;
import com.pm.common.dto.SysResult;
import com.pm.cms.chat.ws.pojo.CmsFodderPublishMsg;
import com.pm.cms.mapper.CmsAdsSettingMapper;
import com.pm.cms.mapper.CmsShelterFodderMapper;
import com.pm.cms.pojo.CmsAdsSetting;
import com.pm.cms.pojo.CmsShelterFodder;
import com.pm.cms.service.CmsShelterFodderService;
import com.pm.cms.vo.FodderShelterGenreVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 功能描述：
 * 【站牌素材impl】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/20 14:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CmsShelterFodderServiceImpl implements CmsShelterFodderService {

    @Autowired
    private CmsAdsSettingMapper cmsAdsSettingMapper;
    @Autowired
    private CmsShelterFodderMapper cmsShelterFodderMapper;

    @Override
    public SysResult insertCmsShelterFodder(CmsFodderPublishMsg cmsFodderPublishMsg) {
        try {

            List<String> iccidList = cmsFodderPublishMsg.getIccids();

            if (CollUtil.isNotEmpty(iccidList)) {
                List<FodderShelterGenreVo> fodderShelterGenreVos = cmsShelterFodderMapper.selectPublishTypeByIccid(iccidList);

                //无前置播放内容时，设置声音
                if (CollUtil.isEmpty(fodderShelterGenreVos)) {
                    for (String iccid : iccidList) {
                        String shelterVoiceArea = cmsAdsSettingMapper.selectVoiceAreaByIccid(iccid);
                        if (shelterVoiceArea.equals(cmsFodderPublishMsg.getFodderArea())) {
                            cmsFodderPublishMsg.setIsVoice(1);
                        } else {
                            cmsFodderPublishMsg.setIsVoice(0);
                        }
                    }
                }

                for (FodderShelterGenreVo fodderShelterGenreVo : fodderShelterGenreVos) {
                    String iccid = fodderShelterGenreVo.getIccid();
                    String shelterVoiceArea = cmsAdsSettingMapper.selectVoiceAreaByIccid(iccid);

                    //有前置播放内容时，设置声音
                    if (shelterVoiceArea.equals(cmsFodderPublishMsg.getFodderArea())) {
                        cmsFodderPublishMsg.setIsVoice(1);
                    } else {
                        cmsFodderPublishMsg.setIsVoice(0);
                    }
                    //TODO 素材类型不一致时，替换处理，参考新版cms系统
                    Integer updateState = 4;
                    updateShelterFodderState(fodderShelterGenreVo, updateState);
                }

                CmsShelterFodder cmsShelterFodder = new CmsShelterFodder();
                BeanUtil.copyProperties(cmsFodderPublishMsg, cmsShelterFodder);
                cmsShelterFodderMapper.insertShelterFodder(cmsShelterFodder);
            } else {
                return SysResult.build(201, "选择的站牌不存在");
            }
        } catch (Exception e) {
            log.error("素材发布失败：{}", e);
            return SysResult.fail();
        }
        return SysResult.oK();
    }

    @Override
    public SysResult updateShelterFodderState(Object object, Integer updateState) {
        try {
            CmsShelterFodder cmsShelterFodder = new CmsShelterFodder();
            DynaBean bean = DynaBean.create(object);

            cmsShelterFodder.setFodderId(bean.get("fodderId"));
            cmsShelterFodder.setIccid(bean.get("iccid"));
            cmsShelterFodder.setShelterMonitor(bean.get("shelterMonitor"));
            cmsShelterFodder.setFodderArea(bean.get("fodderArea"));
            cmsShelterFodder.setUpdateState(updateState);

            cmsShelterFodderMapper.updateCmsShelterFodder(cmsShelterFodder);
        } catch (Exception e) {
            log.error("更新广告屏状态失败：状态码【" + updateState + "】{}", e);
            return SysResult.fail();
        }
        return SysResult.oK();
    }

    @Override
    public void updateCmsFodderSetting(CmsFodderSettingMessage cmsFodderSettingMessage) {
        try {
            String iccid = cmsFodderSettingMessage.getIccid();
            Integer fodderVoiceArea = cmsFodderSettingMessage.getFodderVoiceArea();
            Integer subareaNum = cmsAdsSettingMapper.selectSubareaNumByIccid(iccid);
            CmsAdsSetting cmsAdsSetting = new CmsAdsSetting();
            BeanUtil.copyProperties(cmsFodderSettingMessage, cmsAdsSetting);
            cmsAdsSetting.setFodderArea(fodderVoiceArea);
            cmsAdsSettingMapper.updateCmsAdsSetting(cmsAdsSetting);

            if (subareaNum.equals(cmsFodderSettingMessage.getSubareaNum())) {
                cmsShelterFodderMapper.resetShelterVoice(iccid);
                cmsShelterFodderMapper.updateIsVoice(iccid, fodderVoiceArea);
            } else {
                Integer[] fodderIds = cmsShelterFodderMapper.selectFodderInfoByIccid(iccid);
                if (ArrayUtil.isNotEmpty(fodderIds)) {
                    Integer updateState = 4;
                    cmsShelterFodderMapper.updateFodderStatusByIccid(fodderIds, iccid, updateState);
                }
            }
        } catch (Exception e) {
            log.error("更新广告屏launcher配置失败：{}", e);
        }
    }

}
