package com.company.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.company.project.aop.annotation.LogAnnotation;
import com.company.project.entity.SysDept;
import com.company.project.entity.SysUser;
import com.company.project.service.DeptService;
import com.company.project.utils.DataResult;
import com.company.project.vo.req.DeptAddReqVO;
import com.company.project.vo.req.DeptPageReqVO;
import com.company.project.vo.req.DeptUpdateReqVO;
import com.company.project.vo.req.UserPageUserByDeptReqVO;
import com.company.project.vo.resp.DeptRespNodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-机构管理")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @PostMapping("/dept")
    @ApiOperation(value = "新增组织接口")
    @LogAnnotation(title = "机构管理", action = "新增组织")
    @RequiresPermissions("sys:dept:add")
    public DataResult<SysDept> addDept(@RequestBody @Valid DeptAddReqVO vo) {
        DataResult<SysDept> result = DataResult.success();
        result.setData(deptService.addDept(vo));
        return result;
    }

    @DeleteMapping("/dept/{id}")
    @ApiOperation(value = "删除组织接口")
    @LogAnnotation(title = "机构管理", action = "删除组织")
    @RequiresPermissions("sys:dept:deleted")
    public DataResult deleted(@PathVariable("id") String id) {
        deptService.deleted(id);
        return DataResult.success();
    }

    @PutMapping("/dept")
    @ApiOperation(value = "更新组织信息接口")
    @LogAnnotation(title = "机构管理", action = "更新组织信息")
    @RequiresPermissions("sys:dept:update")
    public DataResult updateDept(@RequestBody @Valid DeptUpdateReqVO vo) {
        deptService.updateDept(vo);
        return DataResult.success();
    }

    @GetMapping("/dept/{id}")
    @ApiOperation(value = "查询组织详情接口")
    @LogAnnotation(title = "机构管理", action = "查询组织详情")
    @RequiresPermissions("sys:dept:detail")
    public DataResult<SysDept> detailInfo(@PathVariable("id") String id) {
        DataResult<SysDept> result = DataResult.success();
        result.setData(deptService.detailInfo(id));
        return result;
    }

    @PostMapping("/depts")
    @ApiOperation(value = "分页获取组织信息接口")
    @LogAnnotation(title = "机构管理", action = "分页获取组织信息")
    @RequiresPermissions("sys:dept:list")
    public DataResult<IPage<SysDept>> pageInfo(@RequestBody DeptPageReqVO vo) {
        return DataResult.success(deptService.pageInfo(vo));
    }

    @GetMapping("/dept/tree")
    @ApiOperation(value = "树型组织列表接口")
    @LogAnnotation(title = "机构管理", action = "树型组织列表")
    @RequiresPermissions(value = {"sys:user:update", "sys:user:add", "sys:dept:add", "sys:dept:update"}, logical = Logical.OR)
    public DataResult<List<DeptRespNodeVO>> getTree(@RequestParam(required = false) String deptId) {
        DataResult<List<DeptRespNodeVO>> result = DataResult.success();
        result.setData(deptService.deptTreeList(deptId));
        return result;
    }

    @PostMapping("/dept/users")
    @ApiOperation(value = "分页获取组织下所有用户接口")
    @LogAnnotation(title = "机构管理", action = "分页获取组织下所有用户")
    @RequiresPermissions("sys:dept:user:list")
    public DataResult<IPage<SysUser>> pageDeptUserInfos(@RequestBody @Valid UserPageUserByDeptReqVO vo) {
        return DataResult.success(deptService.pageDeptUserInfo(vo));
    }

    @GetMapping("/depts")
    @ApiOperation(value = "获取机构列表接口")
    @LogAnnotation(title = "机构管理", action = "获取所有组织机构")
    @RequiresPermissions("sys:dept:list")
    public DataResult<List<SysDept>> getDeptAll() {
        DataResult<List<SysDept>> result = DataResult.success();
        result.setData(deptService.selectAll());
        return result;
    }
}
