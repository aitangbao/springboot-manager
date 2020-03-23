package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.entity.SysRolePermission;
import com.company.project.mapper.SysRolePermissionMapper;
import com.company.project.service.RolePermissionService;
import com.company.project.vo.req.RolePermissionOperationReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements RolePermissionService {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public int removeByRoleId(String roleId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleId);
        return sysRolePermissionMapper.delete(queryWrapper);
    }

    @Override
    public List<String> getPermissionIdsByRoles(List<String> roleIds) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("permission_id").in("role_id", roleIds);
        return sysRolePermissionMapper.selectObjs(queryWrapper);
    }

    @Override
    public void addRolePermission(RolePermissionOperationReqVO vo) {

        Date createTime = new Date();
        List<SysRolePermission> list = new ArrayList<>();
        for (String permissionId : vo.getPermissionIds()) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setCreateTime(createTime);
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermission.setRoleId(vo.getRoleId());
            list.add(sysRolePermission);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", vo.getRoleId());
        sysRolePermissionMapper.delete(queryWrapper);
        this.saveBatch(list);
    }

    @Override
    public int removeByPermissionId(String permissionId) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("permission_id", permissionId);
        return sysRolePermissionMapper.delete(queryWrapper);
    }

    @Override
    public List<String> getRoleIds(String permissionId) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("role_id").eq("permission_id", permissionId);
        return sysRolePermissionMapper.selectObjs(queryWrapper);
    }

    @Override
    public List<String> getPermissionIdsByRoleId(String roleId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("permission_id").eq("role_id", roleId);
        return sysRolePermissionMapper.selectObjs(queryWrapper);
    }
}
