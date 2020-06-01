package com.company.project.controller;

import com.company.project.service.SysDictDetailService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import java.util.List;

import com.company.project.common.utils.DataResult;

import com.company.project.entity.SysDictEntity;
import com.company.project.service.SysDictService;


/**
 * 数据字典表
 *
 * @author manager
 * @email *****@mail.com
 * @date 2020-04-19 10:44:04
 */
@Api(tags = "字典管理")
@RestController
@RequestMapping("/sysDict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SysDictDetailService sysDictDetailService;


    @ApiOperation(value = "新增")
    @PostMapping("/add")
    @RequiresPermissions("sysDict:add")
    public DataResult add(@RequestBody SysDictEntity sysDict) {
        if (StringUtils.isEmpty(sysDict.getName())) {
            return DataResult.fail("字典名称不能为空");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", sysDict.getName());
        SysDictEntity q = sysDictService.getOne(queryWrapper);
        if (q != null) {
            return DataResult.fail("字典名称已存在");
        }
        sysDict.setCreateTime(new Date());
        sysDictService.save(sysDict);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sysDict:delete")
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        sysDictService.removeByIds(ids);
        //删除detail
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("dict_id", ids);
        sysDictDetailService.remove(queryWrapper);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("/update")
    @RequiresPermissions("sysDict:update")
    public DataResult update(@RequestBody SysDictEntity sysDict) {
        if (StringUtils.isEmpty(sysDict.getName())) {
            return DataResult.fail("字典名称不能为空");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", sysDict.getName());
        SysDictEntity q = sysDictService.getOne(queryWrapper);
        if (q != null && !q.getId().equals(sysDict.getId())) {
            return DataResult.fail("字典名称已存在");
        }

        sysDictService.updateById(sysDict);
        return DataResult.success();
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("/listByPage")
    @RequiresPermissions("sysDict:list")
    public DataResult findListByPage(@RequestBody SysDictEntity sysDict) {
        Page page = new Page(sysDict.getPage(), sysDict.getLimit());
        QueryWrapper queryWrapper = new QueryWrapper();
        //查询条件示例
        if (!StringUtils.isEmpty(sysDict.getName())) {
            queryWrapper.like("name", sysDict.getName());
            queryWrapper.or();
            queryWrapper.like("remark", sysDict.getName());
        }
        queryWrapper.orderByAsc("name");
        IPage<SysDictEntity> iPage = sysDictService.page(page, queryWrapper);
        return DataResult.success(iPage);
    }

}
