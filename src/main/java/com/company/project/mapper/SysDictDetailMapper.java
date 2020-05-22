package com.company.project.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.entity.SysDictDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据字典详情
 * 
 * @author manager
 * @email *****@mail.com
 * @date 2020-04-30 15:13:16
 */
@Mapper
public interface SysDictDetailMapper extends BaseMapper<SysDictDetailEntity> {

    IPage<SysDictDetailEntity> listByPage(Page page,@Param(value = "dictId")  String dictId);
}
