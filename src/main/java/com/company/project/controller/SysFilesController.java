package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import com.company.project.common.utils.DataResult;

import com.company.project.entity.SysFilesEntity;
import com.company.project.service.SysFilesService;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件上传
 *
 * @author manager
 * @email *****@mail.com
 * @date 2020-06-09 18:02:50
 */
@RestController
@RequestMapping("/sysFiles")
public class SysFilesController {
    @Autowired
    private SysFilesService sysFilesService;

    @ApiOperation(value = "新增")
    @PostMapping("/upload")
    @RequiresPermissions("sysFiles:add")
    public DataResult add(@RequestParam(value = "file") MultipartFile file) {
        //判断文件是否空
        if (file == null || file.getOriginalFilename() == null || "".equalsIgnoreCase(file.getOriginalFilename().trim())) {
            return DataResult.fail("文件为空");
        }
        sysFilesService.saveFile(file);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sysFiles:delete")
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        sysFilesService.removeByIdsAndFiles(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("/listByPage")
    @RequiresPermissions("sysFiles:list")
    public DataResult findListByPage(@RequestBody SysFilesEntity sysFiles) {
        Page page = new Page(sysFiles.getPage(), sysFiles.getLimit());
        LambdaQueryWrapper<SysFilesEntity> queryWrapper = new LambdaQueryWrapper();
        //查询条件示例
        queryWrapper.orderByDesc(SysFilesEntity::getCreateDate);
        IPage<SysFilesEntity> iPage = sysFilesService.page(page, queryWrapper);
        return DataResult.success(iPage);
    }


}
