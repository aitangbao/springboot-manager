package com.company.project.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus config
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Configuration
public class MyBatisPlusConfig {
    /**
     * 配置mybatis-plus 分页查件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}