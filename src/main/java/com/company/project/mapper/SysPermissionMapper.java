package com.company.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.project.entity.SysPermission;

import java.util.List;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    List<SysPermission> selectInfoByIds (List<String> ids);

}