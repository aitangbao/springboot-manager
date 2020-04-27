package com.company.project.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
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
@Controller
@RequestMapping("/")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;


    /**
     * 跳转到页面
     */
    @GetMapping("/index/sysDict")
    public String sysDict() {
        return "sysdict/list";
    }

    @ApiOperation(value = "新增")
    @PostMapping("sysDict/add")
    @RequiresPermissions("sysDict:add")
    @ResponseBody
    public DataResult add(@RequestBody SysDictEntity sysDict) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", sysDict.getType());
        queryWrapper.eq("code", sysDict.getCode());
        SysDictEntity q = sysDictService.getOne(queryWrapper);
        if (q != null) {
            return DataResult.fail("字典类型-字典码已存在");
        }
        sysDict.setCreateTime(new Date());
        sysDictService.save(sysDict);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("sysDict/delete")
    @RequiresPermissions("sysDict:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        sysDictService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("sysDict/update")
    @RequiresPermissions("sysDict:update")
    @ResponseBody
    public DataResult update(@RequestBody SysDictEntity sysDict) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", sysDict.getType());
        queryWrapper.eq("code", sysDict.getCode());
        SysDictEntity q = sysDictService.getOne(queryWrapper);
        if (q != null && !q.getId().equals(sysDict.getId())) {
            return DataResult.fail("字典类型-字典码已存在");
        }

        sysDictService.updateById(sysDict);
        return DataResult.success();
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("sysDict/listByPage")
    @RequiresPermissions("sysDict:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody SysDictEntity sysDict) {
        Page page = new Page(sysDict.getPage(), sysDict.getLimit());
        QueryWrapper queryWrapper = new QueryWrapper();
        //查询条件示例
        if (!StringUtils.isEmpty(sysDict.getName())) {
            queryWrapper.like("name", sysDict.getName());
        }
        queryWrapper.orderByAsc("name", "order_num");
        IPage<SysDictEntity> iPage = sysDictService.page(page, queryWrapper);
        return DataResult.success(iPage);
    }

}
