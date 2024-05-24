package com.company.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.SysFilesEntity;
import com.company.project.service.SysFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 文件上传
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RestController
@RequestMapping("/sysFiles")
@Api(tags = "文件管理")
public class SysFilesController {
    @Resource
    private SysFilesService sysFilesService;

    @ApiOperation(value = "新增")
    @PostMapping("/upload")
    @SaCheckPermission(value = {"sysFiles:add", "sysContent:update", "sysContent:add"}, mode = SaMode.OR)
    public DataResult add(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        //判断文件是否空
        if (file == null || file.getOriginalFilename() == null || "".equalsIgnoreCase(file.getOriginalFilename().trim())) {
            return DataResult.fail("文件为空");
        }
        return sysFilesService.saveFile(file, request);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete")
    @SaCheckPermission("sysFiles:delete")
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        sysFilesService.removeByIdsAndFiles(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("/listByPage")
    @SaCheckPermission("sysFiles:list")
    public DataResult findListByPage(@RequestBody SysFilesEntity sysFiles) {
        IPage<SysFilesEntity> iPage = sysFilesService.page(sysFiles.getQueryPage(), Wrappers.<SysFilesEntity>lambdaQuery().orderByDesc(SysFilesEntity::getCreateDate));
        return DataResult.success(iPage);
    }


}
