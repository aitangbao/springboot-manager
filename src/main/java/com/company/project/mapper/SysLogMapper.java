package com.company.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.vo.req.SysLogPageReqVO;
import com.company.project.entity.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper extends BaseMapper<SysLog> {


    IPage<SysLog> selectAll(Page page, @Param(value = "vo") SysLogPageReqVO vo);
}