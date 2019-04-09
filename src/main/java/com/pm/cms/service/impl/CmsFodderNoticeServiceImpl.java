package com.pm.cms.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.vcr.VcrClient;
import com.baidubce.services.vcr.model.*;
import com.google.gson.Gson;
import com.pm.cms.auth.AuthService;
import com.pm.cms.common.content.PropertiesContext;
import com.pm.cms.mapper.CmsFodderMapper;
import com.pm.cms.pojo.CmsBaiduAudit;
import com.pm.cms.pojo.CmsFodder;
import com.pm.cms.service.CmsFodderNoticeService;
import com.pm.common.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.pm.cms.common.content.ConstantContext.*;

/**
 * 功能描述：
 * 【视频审核成功回调Impl】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/01/25 18:01
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CmsFodderNoticeServiceImpl implements CmsFodderNoticeService {

    @Autowired
    private CmsFodderMapper cmsFodderMapper;
    @Autowired
    private PropertiesContext propertiesContext;
    @Autowired
    private AuthService authService;

    @Override
    public List<CmsFodder> selectFodderInReviewState() {
        Integer auditStatus = 0;
        return cmsFodderMapper.selectFodderByAuditStatus(auditStatus);
    }

    @Override
    public void disposeFodderVideoAuditResult(CmsFodder cmsFodder) {
        switch (cmsFodder.getFodderType()) {
            case 1:
                checkFodderImage(cmsFodder);
                break;
            case 2:
                checkFodderVideo(cmsFodder);
                break;
            case 3:
                //文字审核为同步，无需在此处操作
                break;
            default:
                break;
        }
    }

    @Override
    public void checkTextInfo(CmsFodder cmsFodder, Integer textType) {

        BceClientConfiguration config = authService.cipherkeySetting();
        config.setCredentials(new DefaultBceCredentials(propertiesContext.getAccessKeyId(), propertiesContext.getSecretKey()));
        String preset = PRESET_NUM.get(textType);
        PutTextRequest request = new PutTextRequest();
        request.setText(cmsFodder.getFodderContext());
        request.setPreset(preset);
        VcrClient vcrClient = new VcrClient(config);
        vcrClient.putText(request);

        PutTextResponse response;
        try {
            response = vcrClient.putText(request);
        } catch (Exception e) {
            log.error("文本审核异常：", e);
            Integer updateState = 4;
            cmsFodder.setRemark("审核异常，请联系管理员");
            updateFodderAuditStatus(cmsFodder, updateState);
            return;
        }

        Gson gson = new Gson();
        String resultJson = gson.toJson(response);
        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
        String label = response.getLabel();

        resultLabletHandle(cmsFodder, jsonObject, label);
    }

    /**
     * 图片审核结果
     *
     * @param cmsFodder CmsFodder实体
     */
    private void checkFodderImage(CmsFodder cmsFodder) {
        CmsBaiduAudit cmsBaiduAudit = cmsFodderMapper.selectBaiduAuditInfo();
        String fodderPath = propertiesContext.getFodderPath() + "fodder/" + cmsFodder.getFodderId();
        String preset = PRESET_NUM.get(cmsBaiduAudit.getImageType());

        BceClientConfiguration config = authService.cipherkeySetting();
        VcrClient vcrClient = new VcrClient(config);
        GetImageAsyncResponse response;
        try {
            response = vcrClient.getImageAsync(fodderPath, preset);
        } catch (Exception e) {
            log.error("图片审核异常：", e);
            Integer updateState = 4;
            cmsFodder.setRemark("审核异常，请联系管理员");
            updateFodderAuditStatus(cmsFodder, updateState);
            return;
        }
        Gson gson = new Gson();
        String resultJson = gson.toJson(response);
        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
        String label = response.getLabel();

        resultLabletHandle(cmsFodder, jsonObject, label);
    }

    /**
     * 视频审核结果
     *
     * @param cmsFodder CmsFodder实体
     */
    private void checkFodderVideo(CmsFodder cmsFodder) {
        String fodderPath = propertiesContext.getFodderPath() + "fodder/" + cmsFodder.getFodderId();

        BceClientConfiguration config = authService.cipherkeySetting();
        VcrClient vcrClient = new VcrClient(config);
        GetMediaResponse auditResult;
        try {
            auditResult = vcrClient.getMedia(fodderPath);
        } catch (Exception e) {
            log.error("视频审核异常：", e);
            Integer updateState = 4;
            cmsFodder.setRemark("审核异常，请联系管理员");
            updateFodderAuditStatus(cmsFodder, updateState);
            return;
        }

        Gson gson = new Gson();
        String resultJson = gson.toJson(auditResult);
        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
        String label = auditResult.getLabel();

        resultLabletHandle(cmsFodder, jsonObject, label);
    }

    /**
     * 对审核结果label进行不同处理
     *
     * @param cmsFodder CmsFodder实体
     * @param vcrResult JSONObject对象
     * @param label     审核结果
     */
    private void resultLabletHandle(CmsFodder cmsFodder, JSONObject vcrResult, String label) {
        Integer updateState;
        switch (label) {
            case NORMAL_RESULT:
                updateState = 1;
                updateFodderAuditStatus(cmsFodder, updateState);
                break;
            case REJECT_RESULT:
                rejectResultHandle(cmsFodder, vcrResult);
                break;
            case REVIEW_RESULT:
                updateState = 3;
                updateFodderAuditStatus(cmsFodder, updateState);
                break;
            default:
                break;
        }
    }

    /**
     * 审核不通过结果处理
     *
     * @param cmsFodder CmsFodder实体
     * @param vcrResult JSONObject对象
     */
    private void rejectResultHandle(CmsFodder cmsFodder, JSONObject vcrResult) {
        Integer updateState;
        Set<String> remarks = new HashSet<>();
        List<CheckResult> results = vcrResult.get("results", List.class);
        for (CheckResult result : results) {
            if (cmsFodder.getFodderType() == 3) {
                for (ResultItem item : result.getItems()) {
                    String extra = item.getExtra();
                    remarks.add(extra);
                }
            } else {
                String type = result.getType();
                String remark = TYPE_MAP.get(type);
                if (StrUtil.isBlank(remark)) {
                    remarks.add("未知因素");
                } else {
                    remarks.add(remark);
                }
            }
        }
        cmsFodder.setRemark("素材包含：" + remarks.toString().replace("[", "").replace("]", ""));
        updateState = 2;
        updateFodderAuditStatus(cmsFodder, updateState);
        deleteFodderFile(cmsFodder);
    }

    /**
     * 删除素材文件
     *
     * @param cmsFodder CmsFodder实体
     */
    private void deleteFodderFile(CmsFodder cmsFodder) {
        boolean flag = FileUtils.deleteFile(cmsFodder.getFodderContext());
        if (flag) {
            log.info("服务器视频文件已删除,文件id为：{}" + cmsFodder.getFodderId());
        } else {
            log.info("服务器视频文件删除失败,文件id为：{}" + cmsFodder.getFodderId());
        }
    }

    /**
     * 修改素材状态
     *
     * @param cmsFodder  CmsFodder实体
     * @param auditState 修改后的状态
     */
    private void updateFodderAuditStatus(CmsFodder cmsFodder, Integer auditState) {
        try {
            cmsFodder.setAuditStatus(auditState);
            cmsFodderMapper.updateCmsFodder(cmsFodder);
        } catch (Exception e) {
            log.error("修改素材状态失败", e);
        }
    }
}
