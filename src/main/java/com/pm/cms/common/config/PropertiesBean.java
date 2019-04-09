package com.pm.cms.common.config;

import com.pm.cms.common.content.PropertiesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @author Liu Xilin
 * @date Created in 2019/01/26 16:57
 */
@Configuration
public class PropertiesBean {

    @Autowired
    private PropertiesContext propertiesContext;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(propertiesContext.getMaxFileSize());
        /// 总上传数据大小
        factory.setMaxRequestSize(propertiesContext.getMaxRequestSize());
        return factory.createMultipartConfig();
    }

}
