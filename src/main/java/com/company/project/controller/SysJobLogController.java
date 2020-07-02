package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.common.aop.annotation.LogAnnotation;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import com.company.project.common.utils.DataResult;

import com.company.project.entity.SysJobLogEntity;
import com.company.project.service.SysJobLogService;

import javax.annotation.Resource;


/**
 * 定时任务日志
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Api(tags = "定时任务日志")
@RestController
@RequestMapping("/sysJobLog")
public class SysJobLogController {
    @Resource
    private SysJobLogService sysJobLogService;

    @ApiOperation(value = "查询分页数据")
    @PostMapping("/listByPage")
    @RequiresPermissions("sysJob:list")
    public DataResult findListByPage(@RequestBody SysJobLogEntity sysJobLog){
        Page page = new Page(sysJobLog.getPage(), sysJobLog.getLimit());
        LambdaQueryWrapper<SysJobLogEntity> queryWrapper = Wrappers.lambdaQuery();
        //查询条件示例
        if (!StringUtils.isEmpty(sysJobLog.getJobId())) {
            queryWrapper.like(SysJobLogEntity::getJobId, sysJobLog.getJobId());
        }
        queryWrapper.orderByDesc(SysJobLogEntity::getCreateTime);
        IPage<SysJobLogEntity> iPage = sysJobLogService.page(page, queryWrapper);
        return DataResult.success(iPage);
    }

    @ApiOperation(value = "清空定时任务日志")
    @DeleteMapping("/delete")
    @RequiresPermissions("sysJob:delete")
    @LogAnnotation(title = "清空")
    public DataResult delete() {
        sysJobLogService.remove(Wrappers.emptyWrapper());
        return DataResult.success();
    }

}
