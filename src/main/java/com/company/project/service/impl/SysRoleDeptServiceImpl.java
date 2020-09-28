package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.SysRoleDeptMapper;
import com.company.project.entity.SysRoleDeptEntity;
import com.company.project.service.SysRoleDeptService;


@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDeptEntity> implements SysRoleDeptService {


}