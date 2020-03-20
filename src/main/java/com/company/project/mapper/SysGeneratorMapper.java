package com.company.project.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.entity.SysGenerator;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    @Select(value = "select TABLE_NAME as tableName from information_schema.TABLES \n" +
            "where TABLE_SCHEMA=(select database()) \n" +
            "and TABLE_NAME not in (select table_name from sys_generator GROUP BY table_name)")
    IPage<SysGenerator> selectAllTables(Page page);
}
