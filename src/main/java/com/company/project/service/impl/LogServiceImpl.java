package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.entity.SysLog;
import com.company.project.mapper.SysLogMapper;
import com.company.project.service.LogService;
import com.company.project.vo.req.SysLogPageReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public IPage<SysLog> pageInfo(SysLogPageReqVO vo) {

        Page page = new Page(vo.getPageNum(), vo.getPageSize());
        return sysLogMapper.selectAll(page, vo);
    }

    @Override
    public void deleted(List<String> logIds) {
        sysLogMapper.deleteBatchIds(logIds);
    }
}
