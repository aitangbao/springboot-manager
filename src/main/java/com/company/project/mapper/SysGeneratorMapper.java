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

    @Select(value = "SELECT\n" +
            "t1.tableName,t2.menu_name, t2.gen_time, t2.id\n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\tTABLE_NAME AS tableName \n" +
            "\tFROM\n" +
            "\t\tinformation_schema.TABLES \n" +
            "\tWHERE\n" +
            "\t\tTABLE_SCHEMA = ( SELECT DATABASE ( ) ) \n" +
            "\t) AS t1\n" +
            "\tLEFT JOIN sys_generator t2 ON t1.tableName = t2.table_name\n" +
            "\tORDER BY gen_time")
    IPage<SysGenerator> selectAllTables(Page<SysGenerator> page);
}
