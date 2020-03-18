package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.entity.SysUserRole;
import com.company.project.exception.BusinessException;
import com.company.project.exception.code.BaseResponseCode;
import com.company.project.mapper.SysUserRoleMapper;
import com.company.project.service.UserRoleService;
import com.company.project.vo.req.UserRoleOperationReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements UserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int removeByRoleId(String roleId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleId);
        return sysUserRoleMapper.delete(queryWrapper);
    }

    @Override
    public List<String> getRoleIdsByUserId(String userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("role_id").eq("user_id", userId);
        return sysUserRoleMapper.selectObjs(queryWrapper);
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserRoleInfo(UserRoleOperationReqVO vo) {
        if(vo.getRoleIds()==null||vo.getRoleIds().isEmpty()){
            return;
        }
        Date createTime=new Date();
        List<SysUserRole> list=new ArrayList<>();
        for (String roleId:vo.getRoleIds()){
            SysUserRole sysUserRole=new SysUserRole();
            sysUserRole.setId(UUID.randomUUID().toString());
            sysUserRole.setCreateTime(createTime);
            sysUserRole.setUserId(vo.getUserId());
            sysUserRole.setRoleId(roleId);
            list.add(sysUserRole);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", vo.getUserId());
        sysUserRoleMapper.delete(queryWrapper);
        //批量插入
        this.saveBatch(list);
    }

    @Override
    public int removeByUserId(String userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        return sysUserRoleMapper.delete(queryWrapper);
    }

    @Override
    public List<String> getUserIdsByRoleIds(List<String> roleIds) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("user_id").in("role_id", roleIds);
        return sysUserRoleMapper.selectObjs(queryWrapper);
    }
}
