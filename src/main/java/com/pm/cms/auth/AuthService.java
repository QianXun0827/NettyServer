package com.pm.cms.auth;


import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.vcr.VcrClient;
import com.baidubce.services.vcr.model.PutMediaRequest;
import com.pm.cms.common.content.PropertiesContext;
import com.pm.cms.mapper.CmsFodderMapper;
import com.pm.cms.pojo.CmsBaiduAudit;
import com.pm.cms.pojo.CmsFodder;
import com.pm.cms.service.CmsFodderNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.pm.cms.common.content.ConstantContext.PRESET_NUM;

/**
 * 功能描述：
 * 【发布内容审核类】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/01/21 11:44
 */
@Component
@Slf4j
public class AuthService {

    @Autowired
    private PropertiesContext propertiesContext;
    @Autowired
    private CmsFodderMapper cmsFodderMapper;
    @Autowired
    private CmsFodderNoticeService cmsFodderNoticeService;

    public void auditFodderContent(CmsFodder cmsFodder) {
        String url = propertiesContext.getFodderPath() + "fodder/" + cmsFodder.getFodderId();
        CmsBaiduAudit cmsBaiduAudit = cmsFodderMapper.selectBaiduAuditInfo();
        switch (cmsFodder.getFodderType()) {
            case 1:
                checkImage(url, cmsBaiduAudit.getImageType());
                break;
            case 2:
                checkVideo(url, cmsBaiduAudit.getVideoType());
                break;
            case 3:
                cmsFodderNoticeService.checkTextInfo(cmsFodder, cmsBaiduAudit.getTextType());
                break;
            default:
                break;
        }
    }

    /**
     * 图片审核，异步
     *
     * @param url       图片链接
     * @param imageType 模版编号
     */
    private void checkImage(String url, Integer imageType) {
        BceClientConfiguration config = cipherkeySetting();

        String preset = PRESET_NUM.get(imageType);
        VcrClient vcrClient = new VcrClient(config);
        vcrClient.putImageAsync(url, preset, propertiesContext.getNotification());

    }

    /**
     * 视频审核接口
     * 视频已提交百度进行异步审核
     *
     * @param videoUrl  视频链接
     * @param videoType 模版编号
     */
    private void checkVideo(String videoUrl, Integer videoType) {
        BceClientConfiguration config = cipherkeySetting();

        String preset = PRESET_NUM.get(videoType);
        PutMediaRequest request = new PutMediaRequest();
        request.setSource(videoUrl);
        request.setPreset(preset);

        request.setNotification(propertiesContext.getNotification());

        VcrClient vcrClient = new VcrClient(config);
        vcrClient.putMedia(request);
    }

    /**
     * 配置审核前参数
     *
     * @return BceClientConfiguration   参数对象
     */
    public BceClientConfiguration cipherkeySetting() {
        BceClientConfiguration config = new BceClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(propertiesContext.getAccessKeyId(), propertiesContext.getSecretKey()));
        config.setEndpoint(propertiesContext.getEndpoint());
        return config;
    }

}
