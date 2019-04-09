package com.pm.cms.chat.util;

import cn.hutool.core.collection.CollUtil;
import com.pm.cms.chat.netty.pojo.Msg;
import com.pm.cms.mapper.CmsFodderMapper;
import com.pm.cms.mapper.CmsShelterFodderMapper;
import com.pm.cms.pojo.CmsShelterFodder;
import com.pm.cms.common.content.PropertiesContext;
import com.pm.cms.vo.CmsFodderPublishVo;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huhaiqiang
 * @date 2019/02/20
 */
@Service("ResendMessage")
@Slf4j
public class ResendMessage {

    @Autowired
    private CmsShelterFodderMapper cmsShelterFodderMapper;
    @Autowired
    private CmsFodderMapper cmsFodderMapper;
    @Autowired
    private PropertiesContext propertiesContext;

    /**
     * 预约播状态
     */
    private static final Integer NUM_TWO = 2;

    public void resendFodderPublishMsg(String iccid, ChannelHandlerContext ctx) {
        List<CmsFodderPublishVo> list = cmsShelterFodderMapper.selectUpdateState(iccid);
        Msg.FodderPublishMessage fodderPublishMessage;
        if (CollUtil.isNotEmpty(list)) {
            for (CmsFodderPublishVo cmsFodderPublishVo : list) {
                Integer fodderId = cmsFodderPublishVo.getFodderId();
                Integer type = cmsFodderMapper.selectFodderInfoById(fodderId).getFodderType();

                if (NUM_TWO.equals(cmsFodderPublishVo.getPublishType())) {
                    fodderPublishMessage = Msg.FodderPublishMessage.newBuilder()
                            .setFodderId(fodderId)
                            .setFodderName(cmsFodderPublishVo.getFodderName())
                            .setFodderType(type)
                            .setPublishType(cmsFodderPublishVo.getPublishType())
                            .setFodderFormat(cmsFodderPublishVo.getFodderFormat())
                            .setFodderPath(propertiesContext.getFodderPath() + "fodder/" + cmsFodderPublishVo.getFodderId())
                            .setFodderContext(cmsFodderPublishVo.getFodderContent())
                            .setFodderArea(cmsFodderPublishVo.getFodderArea())
                            .setShelterMonitor(cmsFodderPublishVo.getShelterMonitor())
                            .setFodderBeginTime(cmsFodderPublishVo.getFodderBeginTime())
                            .setFodderEndTime(cmsFodderPublishVo.getFodderEndTime())
                            .build();
                } else {
                    fodderPublishMessage = Msg.FodderPublishMessage.newBuilder()
                            .setFodderId(fodderId)
                            .setFodderName(cmsFodderPublishVo.getFodderName())
                            .setFodderType(type)
                            .setPublishType(cmsFodderPublishVo.getPublishType())
                            .setFodderFormat(cmsFodderPublishVo.getFodderFormat())
                            .setFodderPath(propertiesContext.getFodderPath() + "fodder/" + cmsFodderPublishVo.getFodderId())
                            .setFodderContext(cmsFodderPublishVo.getFodderContent())
                            .setFodderArea(cmsFodderPublishVo.getFodderArea())
                            .setShelterMonitor(cmsFodderPublishVo.getShelterMonitor())
                            .build();
                }
                Msg.Message message = Msg.Message.newBuilder()
                        .setFodderPublishMessage(fodderPublishMessage)
                        .setMessageType(Msg.MessageType.FODDER_PUBLISH)
                        .build();

                log.info("重发素材发布消息:{}", message);
                ctx.writeAndFlush(message);
            }
        }
    }

    public void resendFodderDownMsg(String iccid, ChannelHandlerContext ctx) {
        List<CmsShelterFodder> list = cmsShelterFodderMapper.selectDownUpdateState(iccid);
        if (CollUtil.isNotEmpty(list)) {
            for (CmsShelterFodder cmsShelterFodder : list) {
                Msg.Message.Builder message = Msg.Message.newBuilder();
                Msg.FodderDownMessage fodderDownMessage = Msg.FodderDownMessage.newBuilder()
                        .setFodderId(cmsShelterFodder.getFodderId())
                        .setIccid(iccid)
                        .setFodderArea(cmsShelterFodder.getFodderArea())
                        .setShelterMonitor(cmsShelterFodder.getShelterMonitor())
                        .build();
                message.setFodderDownMessage(fodderDownMessage)
                        .setMessageType(Msg.MessageType.FODDER_DOWN)
                        .build();

                log.info("重发素材下架消息:{}", message);
                ctx.writeAndFlush(message);
            }
        }
    }
}
