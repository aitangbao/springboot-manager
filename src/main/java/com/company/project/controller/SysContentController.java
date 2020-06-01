package com.company.project.controller;

import com.company.project.common.exception.BusinessException;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.project.common.utils.DataResult;

import com.company.project.entity.SysContentEntity;
import com.company.project.service.SysContentService;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;


/**
 * @author manager
 * @email *****@mail.com
 * @date 2020-05-26 17:00:59
 */
@Api(tags = "文章管理")
@RestController
@RequestMapping("/sysContent")
public class SysContentController {
    @Autowired
    private SysContentService sysContentService;



    @ApiOperation(value = "新增")
    @PostMapping("/add")
    @RequiresPermissions("sysContent:add")
    public DataResult add(@RequestBody SysContentEntity sysContent) {
        sysContent.setCreateTime(new Date());
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
        QueryWrapper queryWrapper = new QueryWrapper();
        //查询条件示例
        if (!StringUtils.isEmpty(sysContent.getTitle())) {
            queryWrapper.like("title", sysContent.getTitle());
        }
        IPage<SysContentEntity> iPage = sysContentService.page(page, queryWrapper);
        return DataResult.success(iPage);
    }


    @ApiOperation(value = "富文本中图片上传")
    @RequiresPermissions(value = {"sysContent:update", "sysContent:add"}, logical = Logical.OR)
    @RequestMapping(value = "/picture/upload", method = RequestMethod.POST)
    public DataResult upload(MultipartFile file) {
        String data = null;
        try {
            //判断是否有文件且是否为图片文件
            BASE64Encoder encoder = new BASE64Encoder();
            // 通过base64来转化图片
            data = encoder.encode(file.getBytes());
        } catch (Exception e) {
            throw new BusinessException("图片上传失败");
        }
        if (!StringUtils.isEmpty(data)) {
            data = "data:image/jpeg;base64," + data;
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("src", data);
            return DataResult.success(resultMap);
        } else {
            return DataResult.fail("上传图片失败");
        }

    }

}
