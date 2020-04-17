package com.company.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.project.entity.SysDept;
import org.apache.ibatis.annotations.Param;

public interface SysDeptMapper extends BaseMapper<SysDept> {


    int updateRelationCode(@Param("oldStr") String oldStr, @Param("newStr") String newStr, @Param("relationCode") String relationCode);

    Integer getMaxDeptCode();
}