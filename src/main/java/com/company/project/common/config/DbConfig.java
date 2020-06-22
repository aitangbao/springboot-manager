package com.company.project.common.config;

import com.company.project.common.exception.BusinessException;
import com.company.project.mapper.GeneratorMapper;
import com.company.project.mapper.SysGeneratorMysqlMapper;
import com.company.project.mapper.SysGeneratorOracleMapper;
import com.company.project.mapper.SysGeneratorSqlServerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 数据库配置
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Configuration
public class DbConfig {
    @Value("${project.database}")
    private String database;
    @Autowired
    private SysGeneratorMysqlMapper sysGeneratorMysqlMapper;
    @Autowired
    private SysGeneratorOracleMapper sysGeneratorOracleMapper;
    @Autowired
    private SysGeneratorSqlServerMapper sysGeneratorSqlServerMapper;

    @Bean
    @Primary
    public GeneratorMapper getGeneratorMapper(){
        if("mysql".equalsIgnoreCase(database)){
            return sysGeneratorMysqlMapper;
        }else if("oracle".equalsIgnoreCase(database)){
            return sysGeneratorOracleMapper;
        }else if("sqlServer".equalsIgnoreCase(database)){
            return sysGeneratorSqlServerMapper;
        }else {
            throw new BusinessException("不支持当前数据库：" + database);
        }
    }
}
