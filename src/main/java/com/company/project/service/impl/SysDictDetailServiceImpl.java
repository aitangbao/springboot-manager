package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.mapper.SysDictDetailMapper;
import com.company.project.mapper.SysDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.entity.SysDictDetailEntity;
import com.company.project.service.SysDictDetailService;

import java.util.List;


@Service("sysDictDetailService")
public class SysDictDetailServiceImpl extends ServiceImpl<SysDictDetailMapper, SysDictDetailEntity> implements SysDictDetailService {

    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;

    @Override
    public IPage<SysDictDetailEntity> listByPage(Page page, String dictId) {
        return sysDictDetailMapper.listByPage(page, dictId);
    }
}