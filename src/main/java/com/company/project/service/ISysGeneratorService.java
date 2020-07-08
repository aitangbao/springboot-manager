package com.company.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.entity.SysGenerator;

/**
 * 代码生成
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface ISysGeneratorService {


    IPage<SysGenerator> selectAllTables(Page<SysGenerator> page, SysGenerator vo);

    byte[] generatorCode(String[] split);
}
