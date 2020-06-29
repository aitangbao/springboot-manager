package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.entity.SysRolePermission;
import com.company.project.mapper.SysRolePermissionMapper;
import com.company.project.service.RolePermissionService;
import com.company.project.vo.req.RolePermissionOperationReqVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限关联
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements RolePermissionService {
    @Override
    public void addRolePermission(RolePermissionOperationReqVO vo) {

        List<SysRolePermission> list = new ArrayList<>();
        for (String permissionId : vo.getPermissionIds()) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermission.setRoleId(vo.getRoleId());
            list.add(sysRolePermission);
        }
        LambdaQueryWrapper<SysRolePermission> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysRolePermission::getRoleId, vo.getRoleId());
        this.remove(queryWrapper);
        this.saveBatch(list);
    }

}
