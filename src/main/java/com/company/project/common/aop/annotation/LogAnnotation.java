package com.company.project.common.aop.annotation;

import java.lang.annotation.*;

/**
* @ClassName:       LogAnnotation
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
