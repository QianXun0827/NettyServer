package com.pm.cms.controller.cms;

import com.pm.cms.service.CmsUserService;
import com.pm.common.dto.SysResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能描述：
 * 【用户Controller】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/25 11:15
 */
@Controller
@ResponseBody
@Slf4j
public class CmsUserController {

    @Autowired
    private CmsUserService cmsUserService;

    @RequestMapping("/userLogin")
    public SysResult login(String userName, String passWord, HttpServletRequest request, HttpServletResponse response) {
        return cmsUserService.userLogin(userName,passWord,request,response);
    }

    @RequestMapping("/userLogout")
    public SysResult logout(HttpServletRequest request, HttpServletResponse response) {
        return cmsUserService.userLogout(request,response);
    }
}
