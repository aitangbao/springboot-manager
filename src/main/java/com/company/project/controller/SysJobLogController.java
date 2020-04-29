package com.company.project.controller;

import com.company.project.common.aop.annotation.LogAnnotation;
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
import java.util.List;
import com.company.project.common.utils.DataResult;

import com.company.project.entity.SysJobLogEntity;
import com.company.project.service.SysJobLogService;



/**
 * 定时任务日志
 *
 * @author manager
 * @email *****@mail.com
 * @date 2020-04-22 14:23:35
 */
@Controller
@RequestMapping("/")
public class SysJobLogController {
    @Autowired
    private SysJobLogService sysJobLogService;


    /**
    * 跳转到页面
    */
    @GetMapping("/index/sysJobLog")
    public String sysJobLog() {
        return "sysjoblog/list";
        }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("sysJobLog/listByPage")
    @RequiresPermissions("sysJobLog:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody SysJobLogEntity sysJobLog){
        Page page = new Page(sysJobLog.getPage(), sysJobLog.getLimit());
        QueryWrapper queryWrapper = new QueryWrapper();
        //查询条件示例
        if (!StringUtils.isEmpty(sysJobLog.getJobId())) {
            queryWrapper.like("job_id", sysJobLog.getJobId());
        }
        queryWrapper.orderByDesc("create_time");
        IPage<SysJobLogEntity> iPage = sysJobLogService.page(page, queryWrapper);
        return DataResult.success(iPage);
    }

    @ApiOperation(value = "清空")
    @DeleteMapping("sysJobLog/delete")
    @RequiresPermissions("sysJobLog:delete")
    @LogAnnotation(title = "清空")
    @ResponseBody
    public DataResult delete() {
        QueryWrapper queryWrapper = new QueryWrapper();
        sysJobLogService.remove(queryWrapper);
        return DataResult.success();
    }


}
