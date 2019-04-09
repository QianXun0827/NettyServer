package com.pm.cms.controller.cms;

import com.pm.cms.common.annotation.PmInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * cms页面管理
 *
 * @author huhaiqiang
 * @date 2018/08/24 09:46
 */
@Controller
@Slf4j
public class PageController {

    /**
     * cms登陆页
     */
    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * cms首页(空白)
     */
    @PmInterceptor(name = "auth")
    @RequestMapping("/cms/index")
    public String defaultPage() {
        return "index";
    }

    /**
     * cms首页
     */
    @PmInterceptor(name = "auth")
    @RequestMapping("/cms/parent")
    public String indexPage() { return "parent"; }

    /**
     * 素材中心
     */
    @PmInterceptor(name = "auth")
    @RequestMapping("/cms/cmsFodderCentre")
    public String cmsFodderCentre() {
        return "cmsFodderCentre";
    }

    /**
     * 播放列表
     */
    @PmInterceptor(name = "auth")
    @RequestMapping("/cms/playlist")
    public String playlist() {
        return "playlist";
    }

    /**
     * cms建模页面
     */
    @PmInterceptor(name = "auth")
    @RequestMapping("/cms/cmsBuildModel")
    public String cmsBuildModel() {
        return "cmsModeling";
    }

    /**
     * 节目管理
     */
    @PmInterceptor(name = "auth")
    @RequestMapping("/cms/cmsFodderManage")
    public String cmsFodderManage() {
        return "cmsFodderManage";
    }

}
