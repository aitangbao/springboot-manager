package com.company.project.controller;

import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.vo.resp.PermissionRespNode;
import com.company.project.entity.SysPermission;
import com.company.project.service.PermissionService;
import com.company.project.common.utils.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-菜单权限管理")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/permission")
    @ApiOperation(value = "新增菜单权限接口")
    @LogAnnotation(title = "菜单权限管理", action = "新增菜单权限")
    @RequiresPermissions("sys:permission:add")
    public DataResult addPermission(@RequestBody @Valid SysPermission vo) {
        return DataResult.success(permissionService.addPermission(vo));
    }

    @DeleteMapping("/permission/{id}")
    @ApiOperation(value = "删除菜单权限接口")
    @LogAnnotation(title = "菜单权限管理", action = "删除菜单权限")
    @RequiresPermissions("sys:permission:deleted")
    public DataResult deleted(@PathVariable("id") String id) {
        permissionService.deleted(id);
        return DataResult.success();
    }

    @PutMapping("/permission")
    @ApiOperation(value = "更新菜单权限接口")
    @LogAnnotation(title = "菜单权限管理", action = "更新菜单权限")
    @RequiresPermissions("sys:permission:update")
    public DataResult updatePermission(@RequestBody @Valid SysPermission vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            return DataResult.fail("id不能为空");
        }
        permissionService.updatePermission(vo);
        return DataResult.success();
    }

    @GetMapping("/permission/{id}")
    @ApiOperation(value = "查询菜单权限接口")
    @LogAnnotation(title = "菜单权限管理", action = "查询菜单权限")
    @RequiresPermissions("sys:permission:detail")
    public DataResult<SysPermission> detailInfo(@PathVariable("id") String id) {
        return DataResult.success(permissionService.detailInfo(id));

    }

    @GetMapping("/permissions")
    @ApiOperation(value = "获取所有菜单权限接口")
    @LogAnnotation(title = "菜单权限管理", action = "获取所有菜单权限")
    @RequiresPermissions("sys:permission:list")
    public DataResult<List<SysPermission>> getAllMenusPermission() {
        return DataResult.success(permissionService.selectAll());
    }

    @GetMapping("/permission/tree")
    @ApiOperation(value = "获取所有目录菜单树接口")
    @LogAnnotation(title = "菜单权限管理", action = "获取所有目录菜单树")
    @RequiresPermissions(value = {"sys:permission:update", "sys:permission:add"}, logical = Logical.OR)
    public DataResult<List<PermissionRespNode>> getAllMenusPermissionTree(@RequestParam(required = false) String permissionId) {
        return DataResult.success(permissionService.selectAllMenuByTree(permissionId));
    }

    @GetMapping("/permission/tree/all")
    @ApiOperation(value = "获取所有目录菜单树接口")
    @LogAnnotation(title = "菜单权限管理", action = "获取所有目录菜单树")
    @RequiresPermissions(value = {"sys:role:update", "sys:role:add"}, logical = Logical.OR)
    public DataResult<List<PermissionRespNode>> getAllPermissionTree() {
        return DataResult.success(permissionService.selectAllByTree());
    }
}
