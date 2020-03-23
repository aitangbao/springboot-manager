package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.service.PermissionService;
import com.company.project.common.utils.DataResult;
import com.company.project.vo.req.PageReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.company.project.service.ISysGeneratorService;
import com.company.project.entity.SysGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author aitangbao
 * @since 2020-03-20
 */
@Api(tags = {""})
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
        return "/sysGenerator/list";
    }

    @ApiOperation(value = "生成")
    @PostMapping("sysGenerator/add")
    @RequiresPermissions("sysGenerator:add")
    @ResponseBody
    public DataResult add(@RequestBody SysGenerator sysGenerator) {
        sysGeneratorService.gen(sysGenerator);
        return DataResult.success();
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("sysGenerator/listByPage")
    @RequiresPermissions("sysGenerator:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody PageReqVO vo) {
        Page page = new Page(vo.getPage(), vo.getLimit());
        IPage<SysGenerator> iPage = sysGeneratorService.selectAllTables(page);
        return DataResult.success(iPage);
    }


    //获取一级菜单list
    @GetMapping("/sysGenerator/getOneMenuList")
    @RequiresPermissions("sysGenerator:list")
    @ResponseBody
    public DataResult getOneMenuList() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("id", "name").eq("type", 1);
        List<Map<String, String>> map = permissionService.list(queryWrapper);
        return DataResult.success(map);
    }

}
