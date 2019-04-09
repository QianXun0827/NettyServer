package com.pm.cms.controller.core;

import com.pm.cms.common.annotation.PmInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Liu Xilin
 * @date Created in 2018/09/20 11:32
 */
@Controller
public class DeployController {

    @RequestMapping(value = "/")
    @PmInterceptor(name = "auth")
    @ResponseBody
    public String getStupid() {
        return "ok!";
    }
}
