package com.pm.cms.controller.cms;

import com.pm.cms.pojo.CmsFodder;
import com.pm.cms.service.CmsFodderNoticeService;
import com.pm.common.annotation.ControllerSysLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 功能描述：
 * 【视频审核成功回调接口】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/01/25 17:53
 */
@Controller
@RequestMapping("/wechat")
@ResponseBody
@Slf4j
public class CmsFodderNoticeController {

    @Autowired
    private CmsFodderNoticeService cmsFodderNoticeService;

    @ControllerSysLogger(type = "/wechat/acceptVideoAuditNotice",description = "百度审核完毕，开始回调")
    @RequestMapping(value = "/acceptVideoAuditNotice")
    public void acceptVideoAuditNotice() {
        List<CmsFodder> cmsFodders = cmsFodderNoticeService.selectFodderInReviewState();
        for (CmsFodder cmsFodder : cmsFodders) {
            cmsFodderNoticeService.disposeFodderVideoAuditResult(cmsFodder);
        }
    }
}
