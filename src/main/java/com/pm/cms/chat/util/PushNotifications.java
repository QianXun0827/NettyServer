package com.pm.cms.chat.util;


import com.pm.cms.chat.netty.group.ChannelGroups;
import com.pm.cms.chat.netty.group.CmsChannelMatcher;
import com.pm.cms.chat.netty.pojo.Msg;
import com.pm.cms.chat.netty.session.Session;
import com.pm.cms.chat.netty.session.SessionManager;
import com.pm.cms.chat.ws.pojo.*;
import com.pm.cms.mapper.CmsFodderMapper;
import com.pm.cms.common.content.PropertiesContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 发送消息工具类
 *
 * @author huhaiqiang
 * @date 2019/02/20
 */
@Service("PushNotifications")
@Slf4j
public class PushNotifications {

    /**
     * 预约播状态
     */
    private static final Integer NUM_TWO = 2;

    @Autowired
    private CmsChannelMatcher cmsChannelMatcher;
    @Autowired
    private PropertiesContext propertiesContext;
    @Autowired
    private CmsFodderMapper cmsFodderMapper;

    /**
     * 修改广告屏launcher配置
     *
     * @param cmsAdsSettingMsg
     */
    public void sendCmsAdsSetting(CmsAdsSettingMsg cmsAdsSettingMsg) {
        List<String> iccids = cmsAdsSettingMsg.getIccids();

        for (String iccid : iccids) {
            Msg.Message.Builder message = Msg.Message.newBuilder();
            Msg.AdsSettingMessage adsSettingMessage;

            adsSettingMessage = Msg.AdsSettingMessage.newBuilder()
                    .setIccid(iccid)
                    .setSubareaNum(cmsAdsSettingMsg.getSubareaNum())
                    .setFodderAreaVoice(cmsAdsSettingMsg.getFodderVoiceArea())
                    .build();
            message.setAdsSettingMessage(adsSettingMessage);
            message.setMessageType(Msg.MessageType.ADS_SETTING);

            Session session = SessionManager.get(iccid);
            if (session != null) {
                session.getChannel().writeAndFlush(message.build());
                log.info("修改广告屏launcher配置信息:{}", message.build());
            }
        }
    }

    /**
     * 素材发布
     *
     * @param cmsFodderPublishMsg CmsFodderMsg实体
     */
    public void sendCmsAds(CmsFodderPublishMsg cmsFodderPublishMsg) {

        Integer fodderId = cmsFodderPublishMsg.getFodderId();
        Integer type = cmsFodderMapper.selectFodderInfoById(fodderId).getFodderType();

        Msg.Message.Builder message = Msg.Message.newBuilder();
        Msg.FodderPublishMessage fodderPublishMessage;

        if (cmsFodderPublishMsg.getPublishType().equals(NUM_TWO)) {
            fodderPublishMessage = Msg.FodderPublishMessage.newBuilder()
                    .setFodderId(fodderId)
                    .setFodderName(cmsFodderPublishMsg.getFodderName())
                    .setFodderType(type)
                    .setPublishType(cmsFodderPublishMsg.getPublishType())
                    .setFodderFormat(cmsFodderPublishMsg.getFodderFormat())
                    .setFodderPath(propertiesContext.getFodderPath() + "fodder/" + cmsFodderPublishMsg.getFodderId())
                    .setFodderContext(cmsFodderPublishMsg.getFodderContent())
                    .setFodderArea(cmsFodderPublishMsg.getFodderArea())
                    .setShelterMonitor(cmsFodderPublishMsg.getShelterMonitor())
                    .setFodderBeginTime(cmsFodderPublishMsg.getFodderBeginTime())
                    .setFodderEndTime(cmsFodderPublishMsg.getFodderEndTime())
                    .build();
        } else {
            fodderPublishMessage = Msg.FodderPublishMessage.newBuilder()
                    .setFodderId(fodderId)
                    .setFodderName(cmsFodderPublishMsg.getFodderName())
                    .setFodderType(type)
                    .setPublishType(cmsFodderPublishMsg.getPublishType())
                    .setFodderFormat(cmsFodderPublishMsg.getFodderFormat())
                    .setFodderPath(propertiesContext.getFodderPath() + "fodder/" + cmsFodderPublishMsg.getFodderId())
                    .setFodderContext(cmsFodderPublishMsg.getFodderContent())
                    .setFodderArea(cmsFodderPublishMsg.getFodderArea())
                    .setShelterMonitor(cmsFodderPublishMsg.getShelterMonitor())
                    .build();
        }
        message.setFodderPublishMessage(fodderPublishMessage)
                .setMessageType(Msg.MessageType.FODDER_PUBLISH)
                .build();

        CmsChannelMatcher.iccids = cmsFodderPublishMsg.getIccids();
        if (ChannelGroups.size() > 0) {
            ChannelGroups.broadcast(message, cmsChannelMatcher);
            CmsChannelMatcher.iccids.clear();
            log.info("素材发布内容:{}", message);
        }
    }

    /**
     * 素材下架
     *
     * @param cmsFodderDownMsg CmsFodderDownMsg实体
     */
    public void sendCmsAdsDown(CmsFodderDownMsg cmsFodderDownMsg) {
        String iccid = cmsFodderDownMsg.getIccid();
        Msg.Message.Builder message = Msg.Message.newBuilder();
        Msg.FodderDownMessage adsDownMessage = Msg.FodderDownMessage.newBuilder()
                .setFodderId(cmsFodderDownMsg.getFodderId())
                .setIccid(iccid)
                .setFodderArea(cmsFodderDownMsg.getFodderArea())
                .setShelterMonitor(cmsFodderDownMsg.getShelterMonitor())
                .build();
        message.setFodderDownMessage(adsDownMessage)
                .setMessageType(Msg.MessageType.FODDER_DOWN)
                .build();

        Session session = SessionManager.get(iccid);
        if (session != null) {
            session.getChannel().writeAndFlush(message);
            log.info("素材下架:{}", message);
        }
    }

}
