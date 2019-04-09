package com.pm.cms.controller.core;

import com.pm.cms.common.annotation.PmInterceptor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @PmInterceptor(name = "auth")
    @RequestMapping(value = "/index")
    public void index() {
    }
}
