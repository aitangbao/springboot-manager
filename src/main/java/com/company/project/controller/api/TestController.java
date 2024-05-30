package com.company.project.controller.api;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.company.project.common.utils.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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


    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    public DataResult login() {
        // 第1步，先登录上
        StpUtil.login(10001);
        // 第2步，获取 Token  相关参数
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 第3步，返回给前端
        return DataResult.success(SaResult.data(tokenInfo));
    }


    @GetMapping("/getCurUserInfo")
    @ApiOperation(value = "获取当前登录人信息示例")
    public DataResult getAppUserInfo() {
        //拿userId与userName
        String userId = StpUtil.getLoginIdAsString();
        return DataResult.success();
    }
}
