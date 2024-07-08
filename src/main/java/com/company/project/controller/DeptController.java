package com.company.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.common.exception.BusinessException;
import com.company.project.entity.SysDept;
import com.company.project.service.DeptService;
import com.company.project.vo.resp.DeptRespNodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 部门管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-机构管理")
public class DeptController {
    @Resource
    private DeptService deptService;

    @PostMapping("/dept")
    @ApiOperation(value = "新增组织接口")
    @LogAnnotation(title = "机构管理", action = "新增组织")
    @SaCheckPermission("sys:dept:add")
    public void addDept(@RequestBody @Valid SysDept vo) {
        deptService.addDept(vo);
    }

    @DeleteMapping("/dept/{id}")
    @ApiOperation(value = "删除组织接口")
    @LogAnnotation(title = "机构管理", action = "删除组织")
    @SaCheckPermission("sys:dept:deleted")
    public void deleted(@PathVariable("id") String id) {
        deptService.deleted(id);
    }

    @PutMapping("/dept")
    @ApiOperation(value = "更新组织信息接口")
    @LogAnnotation(title = "机构管理", action = "更新组织信息")
    @SaCheckPermission("sys:dept:update")
    public void updateDept(@RequestBody SysDept vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            throw new BusinessException("id不能为空");
        }
        deptService.updateDept(vo);
    }

    @GetMapping("/dept/{id}")
    @ApiOperation(value = "查询组织详情接口")
    @LogAnnotation(title = "机构管理", action = "查询组织详情")
    @SaCheckPermission("sys:dept:detail")
    public SysDept detailInfo(@PathVariable("id") String id) {
        return deptService.getById(id);
    }

    @GetMapping("/dept/tree")
    @ApiOperation(value = "树型组织列表接口")
    @LogAnnotation(title = "机构管理", action = "树型组织列表")
    @SaCheckPermission(value = {"sys:user:list", "sys:user:update", "sys:user:add", "sys:dept:add", "sys:dept:update"}, mode = SaMode.OR)
    public List<DeptRespNodeVO> getTree(@RequestParam(required = false) String deptId) {
        return deptService.deptTreeList(deptId, false);
    }

    @GetMapping("/depts")
    @ApiOperation(value = "获取机构列表接口")
    @LogAnnotation(title = "机构管理", action = "获取所有组织机构")
    @SaCheckPermission("sys:dept:list")
    public List<SysDept> getDeptAll() {
        List<SysDept> deptList = deptService.list();
        deptList.stream().forEach(entity -> {
            SysDept parentDept = deptService.getById(entity.getPid());
            if (parentDept != null) {
                entity.setPidName(parentDept.getName());
            }
        });
        return deptList;
    }

}
