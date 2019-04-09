package com.pm.cms.common.annotation;

import java.lang.annotation.*;

/**
 * 拦截注解
 *
 * @author huhaiqiang
 * @date 18/08/11
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PmInterceptor {
    String name() default "";
}
