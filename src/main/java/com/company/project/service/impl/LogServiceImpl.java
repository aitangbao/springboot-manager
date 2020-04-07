package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.entity.SysLog;
import com.company.project.mapper.SysLogMapper;
import com.company.project.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public IPage<SysLog> pageInfo(SysLog vo) {

        Page page = new Page(vo.getPage(), vo.getLimit());
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(vo.getUsername()) ) {
            queryWrapper.like("username", vo.getUsername());
        }
        if (!StringUtils.isEmpty(vo.getOperation()) ) {
            queryWrapper.like("operation", vo.getOperation());
        }
        if (!StringUtils.isEmpty(vo.getStartTime()) ) {
            queryWrapper.gt("create_time", vo.getStartTime());
        }
        if (!StringUtils.isEmpty(vo.getEndTime()) ) {
            queryWrapper.lt("create_time", vo.getEndTime());
        }
        if (!StringUtils.isEmpty(vo.getUserId()) ) {
            queryWrapper.eq("user_id", vo.getUserId());
        }
        queryWrapper.orderByDesc("create_time");
        return sysLogMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void deleted(List<String> logIds) {
        sysLogMapper.deleteBatchIds(logIds);
    }
}
