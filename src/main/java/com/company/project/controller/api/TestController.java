package com.company.project.controller.api;

import com.company.project.common.utils.DataResult;
import com.company.project.service.HttpApiSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * api test示例
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年5月11日
 */
@RestController
@RequestMapping("/app/api")
@Api(tags = "test")
public class TestController {

    @Resource
    HttpApiSessionService httpApiSessionService;

    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    public DataResult login() {
        //TODO 登录

        //生成token
        String token = httpApiSessionService.geneJsonWebToken("123", "测试用户名");
        return DataResult.success(token);
    }


    @GetMapping("/getCurUserInfo")
    @ApiOperation(value = "获取当前登录人信息示例")
    public DataResult getAppUserInfo() {
        //拿userId与userName
        String userId = httpApiSessionService.getCurrentUserId();
        String username = httpApiSessionService.getCurrentUsername();
        return DataResult.success();
    }
}
