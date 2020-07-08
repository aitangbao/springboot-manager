package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import com.company.project.common.utils.DataResult;
import com.company.project.entity.SysContentEntity;
import com.company.project.service.SysContentService;

import javax.annotation.Resource;


/**
 * 文章管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Api(tags = "文章管理")
@RestController
@RequestMapping("/sysContent")
public class SysContentController {
    @Resource
    private SysContentService sysContentService;



    @ApiOperation(value = "新增")
    @PostMapping("/add")
    @RequiresPermissions("sysContent:add")
    public DataResult add(@RequestBody SysContentEntity sysContent) {
        sysContentService.save(sysContent);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sysContent:delete")
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        sysContentService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("/update")
    @RequiresPermissions("sysContent:update")
    public DataResult update(@RequestBody SysContentEntity sysContent) {
        sysContentService.updateById(sysContent);
        return DataResult.success();
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("/listByPage")
    @RequiresPermissions("sysContent:list")
    public DataResult findListByPage(@RequestBody SysContentEntity sysContent) {
        Page page = new Page(sysContent.getPage(), sysContent.getLimit());
        LambdaQueryWrapper<SysContentEntity> queryWrapper = Wrappers.lambdaQuery();
        //查询条件示例
        if (!StringUtils.isEmpty(sysContent.getTitle())) {
            queryWrapper.like(SysContentEntity::getTitle, sysContent.getTitle());
        }
        IPage<SysContentEntity> iPage = sysContentService.page(page, queryWrapper);
        return DataResult.success(iPage);
    }
}
