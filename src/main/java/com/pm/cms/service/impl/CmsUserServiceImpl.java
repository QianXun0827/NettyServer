package com.pm.cms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.pm.cms.common.constant.InterceptorConstant;
import com.pm.cms.common.util.RedisHelps;
import com.pm.cms.mapper.CmsUserMapper;
import com.pm.cms.pojo.PmUser;
import com.pm.cms.service.CmsUserService;
import com.pm.common.dto.SysResult;
import com.pm.common.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * 功能描述：
 * 【用户impl】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/25 11:19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CmsUserServiceImpl implements CmsUserService {

    @Autowired
    private CmsUserMapper cmsUserMapper;
    @Autowired
    private RedisHelps redisHelps;

    @Override
    public SysResult userLogin(String userName, String passWord, HttpServletRequest request, HttpServletResponse response) {
        if (StrUtil.isBlank(userName)) {
            return SysResult.build(303, "用户名不能为空");
        }
        if (StrUtil.isBlank(passWord)) {
            return SysResult.build(304, "用户密码不能为空");
        }

        try {
            PmUser pmUser = cmsUserMapper.userLogin(userName);
            if (null == pmUser) {
                log.error("登陆错误：{}", "用户名不存在");
                return SysResult.fail("用户不存在或密码错误");
            }
            Integer userId = pmUser.getUserId();
            if (null != userId) {
                if (StrUtil.equals(passWord, pmUser.getUserPassword())) {
                    //  用户登陆状态储存至redis
                    redisHelps.set(InterceptorConstant.USERNAME, userName, 3600L);
                    log.info("用户登陆状态已存入redis");
                    //  用户登陆状态储存至cookie
                    String cookieName = AESUtil.aesEncode("userId" + userName);
                    String cookieValue = URLEncoder.encode(userId + "", "utf-8");
                    Cookie cookie = new Cookie(cookieName, cookieValue);
                    cookie.setMaxAge(3600);
                    response.addCookie(cookie);
                    log.info("用户登陆状态已存入cookie");
                } else {
                    log.error("登陆错误：{}", "用户密码错误");
                    return SysResult.fail("用户不存在或密码错误");
                }
            }
        } catch (Exception e) {
            log.error("用户登陆错误：{}", e);
            return SysResult.fail("用户不存在或密码错误");
        }
        return SysResult.oK();
    }

    @Override
    public SysResult userLogout(HttpServletRequest request, HttpServletResponse response) {
        try {
            //用户登陆状态从cookie删除
            Cookie[] cookies = request.getCookies();
            String userName = redisHelps.get(InterceptorConstant.USERNAME);
            String cookieName = AESUtil.aesEncode("userId" + userName);
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
            //用户登陆状态从redis删除
            redisHelps.delete(InterceptorConstant.USERNAME);
        } catch (Exception e) {
            log.error("用户登出错误：{}", e);
            return SysResult.fail();
        }
        return SysResult.oK();
    }
}
