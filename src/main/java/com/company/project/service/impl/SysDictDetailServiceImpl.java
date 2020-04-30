package com.company.project.service.impl;

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
    public List<SysDictDetailEntity> listAll(String dictId) {
        return sysDictDetailMapper.listAll(dictId);
    }
}