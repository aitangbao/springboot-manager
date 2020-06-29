package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.entity.SysRole;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.entity.SysRolePermission;
import com.company.project.entity.SysUserRole;
import com.company.project.mapper.SysRoleMapper;
import com.company.project.service.*;
import com.company.project.vo.req.RolePermissionOperationReqVO;
import com.company.project.vo.resp.PermissionRespNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
@Slf4j
public class RoleServiceImpl  extends ServiceImpl<SysRoleMapper, SysRole> implements RoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private HttpSessionService httpSessionService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRole addRole(SysRole vo) {

        vo.setStatus(1);
        sysRoleMapper.insert(vo);
        if (null != vo.getPermissions() && !vo.getPermissions().isEmpty()) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(vo.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
        }

        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(SysRole vo) {
        SysRole sysRole = sysRoleMapper.selectById(vo.getId());
        if (null == sysRole) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        sysRoleMapper.updateById(vo);
        //删除角色权限关联
        LambdaQueryWrapper<SysRolePermission> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysRolePermission::getRoleId, sysRole.getId());
        rolePermissionService.remove(queryWrapper);
        if (!CollectionUtils.isEmpty(vo.getPermissions())) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(sysRole.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
            // 刷新权限
            httpSessionService.refreshRolePermission(sysRole.getId());
        }
    }

    @Override
    public SysRole detailInfo(String id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        if (sysRole == null) {
            log.error("传入 的 id:{}不合法", id);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        List<PermissionRespNode> permissionRespNodes = permissionService.selectAllByTree();
        LambdaQueryWrapper<SysRolePermission> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.select(SysRolePermission::getPermissionId).eq(SysRolePermission::getRoleId, sysRole.getId());
        Set<Object> checkList =
                new HashSet<>(rolePermissionService.listObjs(queryWrapper));
        setChecked(permissionRespNodes, checkList);
        sysRole.setPermissionRespNodes(permissionRespNodes);
        return sysRole;
    }

    private void setChecked(List<PermissionRespNode> list, Set<Object> checkList) {
        for (PermissionRespNode node : list) {
            if (checkList.contains(node.getId())
                    && (node.getChildren() == null || node.getChildren().isEmpty())) {
                node.setChecked(true);
            }
            setChecked((List<PermissionRespNode>) node.getChildren(), checkList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletedRole(String id) {
        //删除角色
        sysRoleMapper.deleteById(id);
        //删除角色权限关联
        LambdaQueryWrapper<SysRolePermission> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysRolePermission::getRoleId, id);
        rolePermissionService.remove(queryWrapper);
        //删除角色用户关联
        LambdaQueryWrapper<SysUserRole> roleQueryWrapper = new LambdaQueryWrapper();
        roleQueryWrapper.eq(SysUserRole::getRoleId, id);
        userRoleService.remove(roleQueryWrapper);
        // 刷新权限
        httpSessionService.refreshRolePermission(id);
    }

    @Override
    public List<SysRole> getRoleInfoByUserId(String userId) {

        List<String> roleIds = userRoleService.getRoleIdsByUserId(userId);
        if (roleIds.isEmpty()) {
            return null;
        }
        return sysRoleMapper.selectBatchIds(roleIds);
    }

    @Override
    public List<String> getRoleNames(String userId) {

        List<SysRole> sysRoles = getRoleInfoByUserId(userId);
        if (null == sysRoles || sysRoles.isEmpty()) {
            return null;
        }
        List<String> list = sysRoles.stream().map(SysRole::getName).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<SysRole> selectAllRoles() {
        return sysRoleMapper.selectList(Wrappers.emptyWrapper());
    }
}
