package com.pm.cms.controller.core;

import com.pm.cms.common.annotation.PmInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hepeng
 * @ProjectName pm-maven
 * @date 2018/10/16/15:47
 * @Description:
 */
@RequestMapping("/health")
@Controller
public class HealthController {

    @RequestMapping("/check")
    @PmInterceptor(name = "auth")
    @ResponseBody
    public String check() {
        return "OK";
    }

}
