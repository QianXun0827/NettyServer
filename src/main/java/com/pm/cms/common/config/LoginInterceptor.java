package com.pm.cms.common.config;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.pm.cms.common.annotation.PmInterceptor;
import com.pm.cms.common.constant.InterceptorConstant;
import com.pm.cms.common.util.RedisHelps;
import com.pm.cms.mapper.PmUserMapper;
import com.pm.common.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 拦截器
 *
 * @author huhuaiqiang
 * @date 18/08/11
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisHelps redisHelps;
    @Autowired
    private PmUserMapper pmUserMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("" + handler.getClass());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            PmInterceptor pmInterceptor = method.getAnnotation(PmInterceptor.class);
            if (pmInterceptor != null) {
                // 判断是否需要登录
                if (InterceptorConstant.AUTH_LOGIN.equals(pmInterceptor.name())) {
                    log.info("当前拦截的方法:{}", method.getName());
                    String redisName = redisHelps.get(InterceptorConstant.USERNAME);
                    String userId = null;
                    Cookie[] cookies = request.getCookies();
                    try {
                        if (ArrayUtil.isEmpty(cookies)) {
                            response.sendRedirect("/login");
                            return false;
                        }
                        String cookieName = AESUtil.aesEncode("userId" + redisName);
                        for (Cookie cookie : cookies) {
                            if (cookieName.equals(cookie.getName())) {
                                userId = cookie.getValue();
                            }
                        }
                        if (StrUtil.isBlank(redisName) || StrUtil.isBlank(userId)) {
                            response.sendRedirect("/login");
                            return false;
                        }
                        String userName = pmUserMapper.getUserName(Integer.parseInt(userId));
                        log.info("web页面登陆成功，登陆用户名为：{}", userName);
                    } catch (IOException e) {
                        log.error("跳转异常", e);
                        return false;
                    }
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
