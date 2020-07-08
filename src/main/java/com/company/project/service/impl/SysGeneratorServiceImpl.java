package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.common.utils.GenUtils;
import com.company.project.entity.SysGenerator;
import com.company.project.mapper.GeneratorMapper;
import com.company.project.service.ISysGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
@Slf4j
public class SysGeneratorServiceImpl  implements ISysGeneratorService{
    private final GeneratorMapper generatorMapper;

    public SysGeneratorServiceImpl(GeneratorMapper generatorMapper) {
        this.generatorMapper = generatorMapper;
    }

    @Override
    public IPage<SysGenerator> selectAllTables(Page<SysGenerator> page, SysGenerator vo) {
        return generatorMapper.selectAllTables(page, vo);
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
        return generatorMapper.queryTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return generatorMapper.queryColumns(tableName);
    }



}
