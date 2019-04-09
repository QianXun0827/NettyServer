package com.pm.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author huhaiqiang
 */

@MapperScan("com.pm.cms.mapper")
@SpringBootApplication(scanBasePackages = {"com.pm.cms","com.pm.common"})
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }

}

