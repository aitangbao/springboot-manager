package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.SysRole;
import com.company.project.entity.SysRoleDeptEntity;
import com.company.project.service.RoleService;
import com.company.project.service.SysRoleDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    @PostMapping("/role")
    @ApiOperation(value = "新增角色接口")
    @LogAnnotation(title = "角色管理", action = "新增角色")
    @RequiresPermissions("sys:role:add")
    public DataResult addRole(@RequestBody @Valid SysRole vo) {
        roleService.addRole(vo);
        return DataResult.success();
    }

    @DeleteMapping("/role/{id}")
    @ApiOperation(value = "删除角色接口")
    @LogAnnotation(title = "角色管理", action = "删除角色")
    @RequiresPermissions("sys:role:deleted")
    public DataResult deleted(@PathVariable("id") String id) {
        roleService.deletedRole(id);
        return DataResult.success();
    }

    @PutMapping("/role")
    @ApiOperation(value = "更新角色信息接口")
    @LogAnnotation(title = "角色管理", action = "更新角色信息")
    @RequiresPermissions("sys:role:update")
    public DataResult updateDept(@RequestBody SysRole vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            return DataResult.fail("id不能为空");
        }
        roleService.updateRole(vo);
        return DataResult.success();
    }

    @PostMapping("/role/bindDept")
    @ApiOperation(value = "绑定角色部门接口")
    @LogAnnotation(title = "角色管理", action = "绑定角色部门信息")
    @RequiresPermissions("sys:role:bindDept")
    public DataResult bindDept(@RequestBody SysRole vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            return DataResult.fail("id不能为空");
        }
        if (roleService.getById(vo.getId()) == null) {
            return DataResult.fail("获取角色失败");
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
        return DataResult.success();
    }

    @GetMapping("/role/{id}")
    @ApiOperation(value = "查询角色详情接口")
    @LogAnnotation(title = "角色管理", action = "查询角色详情")
    @RequiresPermissions("sys:role:detail")
    public DataResult detailInfo(@PathVariable("id") String id) {
        return DataResult.success(roleService.detailInfo(id));
    }

    @PostMapping("/roles")
    @ApiOperation(value = "分页获取角色信息接口")
    @LogAnnotation(title = "角色管理", action = "分页获取角色信息")
    @RequiresPermissions("sys:role:list")
    @SuppressWarnings("unchecked")
    public DataResult pageInfo(@RequestBody SysRole vo) {
        Page page = new Page(vo.getPage(), vo.getLimit());
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
        return DataResult.success(roleService.page(page, queryWrapper));
    }

}
