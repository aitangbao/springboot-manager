package com.company.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.common.utils.Constant;
import com.company.project.vo.req.RoleAddReqVO;
import com.company.project.vo.req.RoleUpdateReqVO;
import com.company.project.entity.SysRole;
import com.company.project.service.RoleService;
import com.company.project.common.utils.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-角色管理")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/role")
    @ApiOperation(value = "新增角色接口")
    @LogAnnotation(title = "角色管理", action = "新增角色")
    @RequiresPermissions("sys:role:add")
    public DataResult<SysRole> addRole(@RequestBody @Valid RoleAddReqVO vo) {
        return DataResult.success(roleService.addRole(vo));
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
    public DataResult updateDept(@RequestBody @Valid RoleUpdateReqVO vo, HttpServletRequest request) {
        roleService.updateRole(vo, request.getHeader(Constant.ACCESS_TOKEN));
        return DataResult.success();
    }

    @GetMapping("/role/{id}")
    @ApiOperation(value = "查询角色详情接口")
    @LogAnnotation(title = "角色管理", action = "查询角色详情")
    @RequiresPermissions("sys:role:detail")
    public DataResult<SysRole> detailInfo(@PathVariable("id") String id) {
        return DataResult.success(roleService.detailInfo(id));
    }

    @PostMapping("/roles")
    @ApiOperation(value = "分页获取角色信息接口")
    @LogAnnotation(title = "角色管理", action = "分页获取角色信息")
    @RequiresPermissions("sys:role:list")
    public DataResult<IPage<SysRole>> pageInfo(@RequestBody SysRole vo) {
        return DataResult.success(roleService.pageInfo(vo));
    }

}
