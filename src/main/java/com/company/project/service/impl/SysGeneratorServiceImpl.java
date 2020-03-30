package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.company.project.common.utils.GenUtils;
import com.company.project.entity.SysGenerator;
import com.company.project.entity.SysPermission;
import com.company.project.entity.SysRolePermission;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.mapper.SysGeneratorMapper;
import com.company.project.service.ISysGeneratorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.service.PermissionService;
import com.company.project.service.RolePermissionService;
import com.company.project.common.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aitangbao
 * @since 2020-03-20
 */
@Service
@Slf4j
public class SysGeneratorServiceImpl extends ServiceImpl<SysGeneratorMapper, SysGenerator> implements ISysGeneratorService {

    @Autowired
    private SysGeneratorMapper sysGeneratorMapper;

    @Override
    public IPage<SysGenerator> selectAllTables(Page page, SysGenerator vo) {
        return sysGeneratorMapper.selectAllTables(page, vo);
    }

    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for(String tableName : tableNames){
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    public Map<String, String> queryTable(String tableName) {
        return sysGeneratorMapper.queryTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return sysGeneratorMapper.queryColumns(tableName);
    }



}
