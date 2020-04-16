package com.company.project.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.service.PermissionService;
import com.company.project.common.utils.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.company.project.service.ISysGeneratorService;
import com.company.project.entity.SysGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author aitangbao
 * @since 2020-03-20
 */
@Api(tags = "系统模块-代码生成")
@Slf4j
@Controller
@RequestMapping("/")
public class SysGeneratorController {

    @Resource
    private ISysGeneratorService sysGeneratorService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/sysGenerator")
    public String sysGenerator() {
        return "generator/list";
    }

    /**
     * 生成代码
     */
    @ApiOperation(value = "生成")
    @GetMapping("sysGenerator/add")
    @RequiresPermissions("sysGenerator:add")
    @LogAnnotation(title = "代码生成", action = "代码生成")
    public void code(String tables, HttpServletResponse response) throws IOException {
        byte[] data = sysGeneratorService.generatorCode(tables.split(","));

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"manager.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("sysGenerator/listByPage")
    @RequiresPermissions("sysGenerator:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody SysGenerator vo) {
        Page page = new Page(vo.getPage(), vo.getLimit());
        IPage<SysGenerator> iPage = sysGeneratorService.selectAllTables(page, vo);
        return DataResult.success(iPage);
    }


}
