package com.pm.cms.common.content;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 功能描述：
 * 【配置文件读取】
 *
 * @author huhaiqiang
 * @version V1.0
 * @date 2019/02/21 17:56
 */
@Component
@Getter
@PropertySource({"classpath:/content.properties"})
public class PropertiesContext {

    /**
     * netty端口
     */
    @Value("${netty.port}")
    private int nettyPort;

    /**
     * websocket端口
     */
    @Value("${websocket.port}")
    private int websocketPort;

    /**
     * 保存路径
     */
    @Value("${REPOSITORY_PATH}")
    private String repositoryPath;

    /**
     * 保存路径
     */
    @Value("${IMAGE_BASE_URL}")
    private String imageBaseUrl;

    /**
     * 百度审核完成通知接口
     */
    @Value("${baidu.fodder.notice}")
    private String notification;

    /**
     * 审核配置
     */
    @Value("${baidu.fodder.endpoint}")
    private String endpoint;

    /**
     * 素材下载路径
     */
    @Value("${fodder.path}")
    private String fodderPath;

    /**
     * 单个数据大小
     */
    @Value("${spring.server.MaxFileSize}")
    private String maxFileSize;

    /**
     * 单个数据大小
     */
    @Value("${spring.server.MaxRequestSize}")
    private String maxRequestSize;

    /**
     * accessKeyId
     */
    @Value("${baidu.accessKeyId}")
    private String accessKeyId;

    /**
     * secretKey
     */
    @Value("${baidu.secretKey}")
    private String secretKey;


}
