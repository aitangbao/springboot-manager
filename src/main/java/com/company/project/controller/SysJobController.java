package com.company.project.controller;

import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.job.utils.ScheduleJob;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.company.project.common.utils.DataResult;

import com.company.project.entity.SysJobEntity;
import com.company.project.service.SysJobService;


/**
 * 定时任务
 *
 * @author manager
 * @email *****@mail.com
 * @date 2020-04-22 14:23:36
 */
@Controller
@RequestMapping("/")
public class SysJobController {
    @Autowired
    private SysJobService sysJobService;


    /**
     * 跳转到页面
     */
    @GetMapping("/index/sysJob")
    public String sysJob() {
        return "sysjob/list";
    }

    @ApiOperation(value = "新增")
    @LogAnnotation(title = "新增")
    @PostMapping("sysJob/add")
    @RequiresPermissions("sysJob:add")
    @ResponseBody
    public DataResult add(@RequestBody SysJobEntity sysJob) {
        if (!isValidExpression(sysJob.getCronExpression())) {
            return DataResult.fail("cron表达式有误");
        }
        DataResult dataResult = ScheduleJob.judgeBean(sysJob.getBeanName());
        if (BaseResponseCode.SUCCESS.getCode() != dataResult.getCode()) {
            return dataResult;
        }

        sysJobService.saveJob(sysJob);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("sysJob/delete")
    @RequiresPermissions("sysJob:delete")
    @LogAnnotation(title = "删除")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        sysJobService.delete(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("sysJob/update")
    @RequiresPermissions("sysJob:update")
    @LogAnnotation(title = "更新")
    @ResponseBody
    public DataResult update(@RequestBody SysJobEntity sysJob) {
        if (!isValidExpression(sysJob.getCronExpression())) {
            return DataResult.fail("cron表达式有误");
        }
        DataResult dataResult = ScheduleJob.judgeBean(sysJob.getBeanName());
        if (BaseResponseCode.SUCCESS.getCode() != dataResult.getCode()) {
            return dataResult;
        }

        sysJobService.updateJobById(sysJob);
        return DataResult.success();
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("sysJob/listByPage")
    @RequiresPermissions("sysJob:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody SysJobEntity sysJob) {
        Page page = new Page(sysJob.getPage(), sysJob.getLimit());
        QueryWrapper queryWrapper = new QueryWrapper();
        //查询条件示例
        if (!StringUtils.isEmpty(sysJob.getBeanName())) {
            queryWrapper.like("bean_name", sysJob.getBeanName());
        }
        IPage<SysJobEntity> iPage = sysJobService.page(page, queryWrapper);
        return DataResult.success(iPage);
    }


    /**
     * 立即执行任务
     */
    @LogAnnotation(title = "立即执行任务")
    @PostMapping("/sysJob/run")
    @RequiresPermissions("sysJob:run")
    @ResponseBody
    public DataResult run(@RequestBody List<String> ids) {
        sysJobService.run(ids);

        return DataResult.success();
    }

    /**
     * 暂停定时任务
     */
    @LogAnnotation(title = "暂停定时任务")
    @PostMapping("/sysJob/pause")
    @RequiresPermissions("sysJob:pause")
    @ResponseBody
    public DataResult pause(@RequestBody List<String> ids) {
        sysJobService.pause(ids);

        return DataResult.success();
    }

    /**
     * 恢复定时任务
     */
    @LogAnnotation(title = "恢复定时任务")
    @PostMapping("/sysJob/resume")
    @RequiresPermissions("sysJob:resume")
    @ResponseBody
    public DataResult resume(@RequestBody List<String> ids) {
        sysJobService.resume(ids);

        return DataResult.success();
    }

    public static boolean isValidExpression(String cronExpression) {
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setCronExpression(cronExpression);
            Date date = trigger.computeFirstFireTime(null);
            return date != null && date.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    @ApiOperation(value = "获取运行时间")
    @LogAnnotation(title = "获取运行时间")
    @GetMapping("sysJob/getRecentTriggerTime")
    @RequiresPermissions("sysJob:add")
    @ResponseBody
    public DataResult getRecentTriggerTime(String cron) {
        List<String> list = new ArrayList<String>();
        try {
            CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
            cronTriggerImpl.setCronExpression(cron);
            // 这个是重点，一行代码搞定
            List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, 5);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Date date : dates) {
                list.add(dateFormat.format(date));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return DataResult.success(list);
    }

}
