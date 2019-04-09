package com.pm.cms.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 功能描述：
 * 【配置类】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/25 10:37
 */
@Configuration
public class CmsWebConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor getManageInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getManageInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/loginPage",
                        "/downLoad/**",
                        "/wechat/**"
                );
    }
}
