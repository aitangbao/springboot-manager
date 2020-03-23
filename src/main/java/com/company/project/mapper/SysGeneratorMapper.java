package com.company.project.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.entity.SysGenerator;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author aitangbao
 * @since 2020-03-20
 */
public interface SysGeneratorMapper extends BaseMapper<SysGenerator> {

    IPage<SysGenerator> selectAllTables(Page<SysGenerator> page, @Param(value = "vo") SysGenerator vo);
}
