package com.company.project.common.aop.annotation;

import java.lang.annotation.*;

/**
 * LogAnnotation
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    /** 模块 */
    String title() default "";

    /** 功能 */
    String action() default "";
}
