package com.pm.cms.service;

import com.pm.common.dto.SysResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能描述：
 * 【用户Service】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/25 11:19
 */
public interface CmsUserService {

    /**
     * 用户登陆
     *
     * @param userName 用户名
     * @param passWord 用户密码
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return SysResult
     */
    SysResult userLogin(String userName, String passWord, HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户登出
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return SysResult
     */
    SysResult userLogout(HttpServletRequest request, HttpServletResponse response);
}
