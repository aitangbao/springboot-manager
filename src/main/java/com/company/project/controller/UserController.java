package com.company.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.SysUser;
import com.company.project.entity.SysUserRole;
import com.company.project.service.HomeService;
import com.company.project.service.UserRoleService;
import com.company.project.service.UserService;
import com.company.project.vo.req.UserRoleOperationReqVO;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 用户管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RestController
@Api(tags = "组织模块-用户管理")
@RequestMapping("/sys")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private HomeService homeService;
    /**
     * 获取验证码图片
     * Gets captcha code.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException the io exception
     */
    @RequestMapping("/getVerify")
    public void getCaptchaCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        captcha.setLen(2);
        CaptchaUtil.out(captcha, request, response);
    }


    @PostMapping(value = "/user/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult login(@RequestBody @Valid SysUser vo, HttpServletRequest request) {
        //判断验证码
        if (!CaptchaUtil.ver(vo.getCaptcha(), request)) {
            // 清除session中的验证码
            CaptchaUtil.clear(request);
            return DataResult.fail("验证码错误！");
        }
        return DataResult.success(userService.login(vo));
    }
    @GetMapping("/home")
    @ApiOperation(value = "获取首页数据接口")
    public DataResult getHomeInfo() {
        //通过access_token拿userId
        String userId = StpUtil.getLoginIdAsString();
        DataResult result = DataResult.success();
        result.setData(homeService.getHomeInfo(userId));
        return result;
    }

    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册接口")
    public DataResult register(@RequestBody @Valid SysUser vo) {
        userService.register(vo);
        return DataResult.success();
    }

    @GetMapping("/user/unLogin")
    @ApiOperation(value = "引导客户端去登录")
    public DataResult unLogin() {
        return DataResult.getResult(BaseResponseCode.TOKEN_ERROR);
    }

    @PutMapping("/user")
    @ApiOperation(value = "更新用户信息接口")
    @LogAnnotation(title = "用户管理", action = "更新用户信息")
    @SaCheckPermission("sys:user:update")
    public DataResult updateUserInfo(@RequestBody SysUser vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            return DataResult.fail("id不能为空");
        }

        userService.updateUserInfo(vo);
        return DataResult.success();
    }

    @PutMapping("/user/info")
    @ApiOperation(value = "更新用户信息接口")
    @LogAnnotation(title = "用户管理", action = "更新用户信息")
    public DataResult updateUserInfoById(@RequestBody SysUser vo) {
        userService.updateUserInfoMy(vo);
        return DataResult.success();
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "查询用户详情接口")
    @LogAnnotation(title = "用户管理", action = "查询用户详情")
    @SaCheckPermission("sys:user:detail")
    public DataResult detailInfo(@PathVariable("id") String id) {
        return DataResult.success(userService.getById(id));
    }

    @GetMapping("/user")
    @ApiOperation(value = "查询用户详情接口")
    @LogAnnotation(title = "用户管理", action = "查询用户详情")
    public DataResult youSelfInfo() {
        String userId = StpUtil.getLoginIdAsString();
        return DataResult.success(userService.getById(userId));
    }

    @PostMapping("/users")
    @ApiOperation(value = "分页获取用户列表接口")
    @SaCheckPermission("sys:user:list")
    @LogAnnotation(title = "用户管理", action = "分页获取用户列表")
    public DataResult pageInfo(@RequestBody SysUser vo) {
        return DataResult.success(userService.pageInfo(vo));
    }

    @PostMapping("/user")
    @ApiOperation(value = "新增用户接口")
    @SaCheckPermission("sys:user:add")
    @LogAnnotation(title = "用户管理", action = "新增用户")
    public DataResult addUser(@RequestBody @Valid SysUser vo) {
        userService.addUser(vo);
        return DataResult.success();
    }

    @GetMapping("/user/logout")
    @ApiOperation(value = "退出接口")
    public DataResult logout() {
        StpUtil.logout();
        return DataResult.success();
    }

    @PutMapping("/user/pwd")
    @ApiOperation(value = "修改密码接口")
    @LogAnnotation(title = "用户管理", action = "更新密码")
    public DataResult updatePwd(@RequestBody SysUser vo) {
        if (StringUtils.isEmpty(vo.getOldPwd()) || StringUtils.isEmpty(vo.getNewPwd())) {
            return DataResult.fail("旧密码与新密码不能为空");
        }
        vo.setId(StpUtil.getLoginIdAsString());
        userService.updatePwd(vo);
        return DataResult.success();
    }

    @DeleteMapping("/user")
    @ApiOperation(value = "删除用户接口")
    @LogAnnotation(title = "用户管理", action = "删除用户")
    @SaCheckPermission("sys:user:deleted")
    public DataResult deletedUser(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds) {
        //删除用户， 删除redis的绑定的角色跟权限
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SysUser::getId, userIds);
        userService.remove(queryWrapper);
        return DataResult.success();
    }

    @GetMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-获取所有角色接口")
    @LogAnnotation(title = "用户管理", action = "赋予角色-获取所有角色接口")
    @SaCheckPermission("sys:user:role:detail")
    public DataResult getUserOwnRole(@PathVariable("userId") String userId) {
        DataResult result = DataResult.success();
        result.setData(userService.getUserOwnRole(userId));
        return result;
    }

    @PutMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-用户赋予角色接口")
    @LogAnnotation(title = "用户管理", action = "赋予角色-用户赋予角色接口")
    @SaCheckPermission("sys:user:role:update")
    public DataResult setUserOwnRole(@PathVariable("userId") String userId, @RequestBody List<String> roleIds) {

        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        userRoleService.remove(queryWrapper);
        if (!CollectionUtils.isEmpty(roleIds)) {
            UserRoleOperationReqVO reqVO = new UserRoleOperationReqVO();
            reqVO.setUserId(userId);
            reqVO.setRoleIds(roleIds);
            userRoleService.addUserRoleInfo(reqVO);
        }
        return DataResult.success();
    }
}
