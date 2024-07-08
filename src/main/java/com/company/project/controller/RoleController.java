package com.company.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.common.exception.BusinessException;
import com.company.project.entity.SysRole;
import com.company.project.entity.SysRoleDeptEntity;
import com.company.project.service.RolePermissionService;
import com.company.project.service.RoleService;
import com.company.project.service.SysRoleDeptService;
import com.company.project.vo.req.RolePermissionOperationReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-角色管理")
public class RoleController {
    @Resource
    private RoleService roleService;
    @Resource
    private SysRoleDeptService sysRoleDeptService;
    @Resource
    private RolePermissionService rolePermissionService;

    @PostMapping("/role")
    @ApiOperation(value = "新增角色接口")
    @LogAnnotation(title = "角色管理", action = "新增角色")
    @SaCheckPermission("sys:role:add")
    public void addRole(@RequestBody @Valid SysRole vo) {
        roleService.addRole(vo);
    }

    @DeleteMapping("/role/{id}")
    @ApiOperation(value = "删除角色接口")
    @LogAnnotation(title = "角色管理", action = "删除角色")
    @SaCheckPermission("sys:role:deleted")
    public void deleted(@PathVariable("id") String id) {
        roleService.deletedRole(id);
    }

    @PutMapping("/role")
    @ApiOperation(value = "更新角色信息接口")
    @LogAnnotation(title = "角色管理", action = "更新角色信息")
    @SaCheckPermission("sys:role:update")
    public void updateDept(@RequestBody SysRole vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            throw new BusinessException("id不能为空");
        }
        roleService.updateRole(vo);
    }

    @PostMapping("/role/bindDept")
    @ApiOperation(value = "绑定角色部门接口")
    @LogAnnotation(title = "角色管理", action = "绑定角色部门信息")
    @SaCheckPermission("sys:role:bindDept")
    public void bindDept(@RequestBody SysRole vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            throw new BusinessException("id不能为空");
        }
        if (roleService.getById(vo.getId()) == null) {
            throw new BusinessException("获取角色失败");
        }

        //先删除所有绑定
        sysRoleDeptService.remove(Wrappers.<SysRoleDeptEntity>lambdaQuery().eq(SysRoleDeptEntity::getRoleId, vo.getId()));
        //如果不是自定义
        if (vo.getDataScope() != 2) {
            vo.setDepts(null);
        }
        if (!CollectionUtils.isEmpty(vo.getDepts())) {
            List<SysRoleDeptEntity> list = new ArrayList<>();
            for (String deptId : vo.getDepts()) {
                SysRoleDeptEntity sysRoleDeptEntity = new SysRoleDeptEntity();
                sysRoleDeptEntity.setDeptId(deptId);
                sysRoleDeptEntity.setRoleId(vo.getId());
                list.add(sysRoleDeptEntity);
            }
            sysRoleDeptService.saveBatch(list);
        }
        roleService.updateById(new SysRole().setId(vo.getId()).setDataScope(vo.getDataScope()));
    }

    @GetMapping("/role/{id}")
    @ApiOperation(value = "查询角色详情接口")
    @LogAnnotation(title = "角色管理", action = "查询角色详情")
    @SaCheckPermission("sys:role:detail")
    public SysRole detailInfo(@PathVariable("id") String id) {
        return roleService.detailInfo(id);
    }

    @PostMapping("/roles")
    @ApiOperation(value = "分页获取角色信息接口")
    @LogAnnotation(title = "角色管理", action = "分页获取角色信息")
    @SaCheckPermission("sys:role:list")
    @SuppressWarnings("unchecked")
    public Page<SysRole> pageInfo(@RequestBody SysRole vo) {
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(vo.getName())) {
            queryWrapper.like(SysRole::getName, vo.getName());
        }
        if (!StringUtils.isEmpty(vo.getStartTime())) {
            queryWrapper.gt(SysRole::getCreateTime, vo.getStartTime());
        }
        if (!StringUtils.isEmpty(vo.getEndTime())) {
            queryWrapper.lt(SysRole::getCreateTime, vo.getEndTime());
        }
        if (!StringUtils.isEmpty(vo.getStatus())) {
            queryWrapper.eq(SysRole::getStatus, vo.getStatus());
        }
        queryWrapper.orderByDesc(SysRole::getCreateTime);
        return roleService.page(vo.getQueryPage(), queryWrapper);
    }

    @PostMapping("/role/permission")
    @ApiOperation(value = "修改或者新增角色菜单权限接口")
    @LogAnnotation(title = "角色和菜单关联接口", action = "修改或者新增角色菜单权限")
    @SaCheckPermission(value = {"sys:role:update", "sys:role:add"}, mode = SaMode.OR)
    public void operationRolePermission(@RequestBody @Valid RolePermissionOperationReqVO vo) {
        rolePermissionService.addRolePermission(vo);
    }
}
