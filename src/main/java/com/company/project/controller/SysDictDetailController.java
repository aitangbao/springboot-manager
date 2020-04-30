package com.company.project.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.company.project.common.utils.DataResult;

import com.company.project.entity.SysDictDetailEntity;
import com.company.project.service.SysDictDetailService;



/**
 * 数据字典详情
 *
 * @author manager
 * @email *****@mail.com
 * @date 2020-04-30 15:13:16
 */
@Controller
@RequestMapping("/")
public class SysDictDetailController {
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @ApiOperation(value = "新增")
    @PostMapping("sysDictDetail/add")
    @RequiresPermissions("sysDict:add")
    @ResponseBody
    public DataResult add(@RequestBody SysDictDetailEntity sysDictDetail){
        if (StringUtils.isEmpty(sysDictDetail.getValue())) {
            return DataResult.fail("字典值不能为空");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("value", sysDictDetail.getValue());
        queryWrapper.eq("dict_id", sysDictDetail.getDictId());
        SysDictDetailEntity q = sysDictDetailService.getOne(queryWrapper);
        if (q != null) {
            return DataResult.fail("字典名称-字典值已存在");
        }
        sysDictDetail.setCreateTime(new Date());
        sysDictDetailService.save(sysDictDetail);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("sysDictDetail/delete")
    @RequiresPermissions("sysDict:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids){
        sysDictDetailService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("sysDictDetail/update")
    @RequiresPermissions("sysDict:update")
    @ResponseBody
    public DataResult update(@RequestBody SysDictDetailEntity sysDictDetail){
        if (StringUtils.isEmpty(sysDictDetail.getValue())) {
            return DataResult.fail("字典值不能为空");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("value", sysDictDetail.getValue());
        queryWrapper.eq("dict_id", sysDictDetail.getDictId());
        SysDictDetailEntity q = sysDictDetailService.getOne(queryWrapper);
        if (q != null && !q.getId().equals(sysDictDetail.getId())) {
            return DataResult.fail("字典名称-字典值已存在");
        }

        sysDictDetailService.updateById(sysDictDetail);
        return DataResult.success();
    }

    @ApiOperation(value = "查询列表数据")
    @PostMapping("sysDictDetail/list")
    @RequiresPermissions("sysDict:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody SysDictDetailEntity sysDictDetail){
        if (StringUtils.isEmpty(sysDictDetail.getDictId())) {
            return DataResult.success(new ArrayList<>());
        }
        List<SysDictDetailEntity> list = sysDictDetailService.listAll(sysDictDetail.getDictId());
        return DataResult.success(list);
    }

}
