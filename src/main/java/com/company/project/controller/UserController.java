package com.company.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.SysUser;
import com.company.project.entity.SysUserRole;
import com.company.project.service.HomeService;
import com.company.project.service.UserRoleService;
import com.company.project.service.UserService;
import com.company.project.vo.req.UserRoleOperationReqVO;
import com.company.project.vo.resp.HomeRespVO;
import com.company.project.vo.resp.LoginRespVO;
import com.company.project.vo.resp.UserOwnRoleRespVO;
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
    public LoginRespVO login(@RequestBody @Valid SysUser vo, HttpServletRequest request) {
        //判断验证码
        if (!CaptchaUtil.ver(vo.getCaptcha(), request)) {
            // 清除session中的验证码
            CaptchaUtil.clear(request);
            throw new BusinessException("验证码错误！");
        }
        return userService.login(vo);
    }

    @GetMapping("/home")
    @ApiOperation(value = "获取首页数据接口")
    public HomeRespVO getHomeInfo() {
        //通过access_token拿userId
        String userId = StpUtil.getLoginIdAsString();
        DataResult result = DataResult.success();
        return homeService.getHomeInfo(userId);
    }

    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册接口")
    public void register(@RequestBody @Valid SysUser vo) {
        userService.register(vo);
    }

    @GetMapping("/user/unLogin")
    @ApiOperation(value = "引导客户端去登录")
    public void unLogin() {
        throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
    }

    @PutMapping("/user")
    @ApiOperation(value = "更新用户信息接口")
    @LogAnnotation(title = "用户管理", action = "更新用户信息")
    @SaCheckPermission("sys:user:update")
    public void updateUserInfo(@RequestBody SysUser vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            throw new BusinessException("id不能为空");
        }

        userService.updateUserInfo(vo);
    }

    @PutMapping("/user/info")
    @ApiOperation(value = "更新用户信息接口")
    @LogAnnotation(title = "用户管理", action = "更新用户信息")
    public void updateUserInfoById(@RequestBody SysUser vo) {
        userService.updateUserInfoMy(vo);
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "查询用户详情接口")
    @LogAnnotation(title = "用户管理", action = "查询用户详情")
    @SaCheckPermission("sys:user:detail")
    public SysUser detailInfo(@PathVariable("id") String id) {
        return userService.getById(id);
    }

    @GetMapping("/user")
    @ApiOperation(value = "查询用户详情接口")
    @LogAnnotation(title = "用户管理", action = "查询用户详情")
    public SysUser youSelfInfo() {
        String userId = StpUtil.getLoginIdAsString();
        return userService.getById(userId);
    }

    @PostMapping("/users")
    @ApiOperation(value = "分页获取用户列表接口")
    @SaCheckPermission("sys:user:list")
    @LogAnnotation(title = "用户管理", action = "分页获取用户列表")
    public IPage<SysUser> pageInfo(@RequestBody SysUser vo) {
        return userService.pageInfo(vo);
    }

    @PostMapping("/user")
    @ApiOperation(value = "新增用户接口")
    @SaCheckPermission("sys:user:add")
    @LogAnnotation(title = "用户管理", action = "新增用户")
    public void addUser(@RequestBody @Valid SysUser vo) {
        userService.addUser(vo);
    }

    @GetMapping("/user/logout")
    @ApiOperation(value = "退出接口")
    public void logout() {
        StpUtil.logout();
    }

    @PutMapping("/user/pwd")
    @ApiOperation(value = "修改密码接口")
    @LogAnnotation(title = "用户管理", action = "更新密码")
    public void updatePwd(@RequestBody SysUser vo) {
        if (StringUtils.isEmpty(vo.getOldPwd()) || StringUtils.isEmpty(vo.getNewPwd())) {
            throw new BusinessException("旧密码与新密码不能为空");
        }
        vo.setId(StpUtil.getLoginIdAsString());
        userService.updatePwd(vo);
    }

    @DeleteMapping("/user")
    @ApiOperation(value = "删除用户接口")
    @LogAnnotation(title = "用户管理", action = "删除用户")
    @SaCheckPermission("sys:user:deleted")
    public void deletedUser(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds) {
        //删除用户， 删除redis的绑定的角色跟权限
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SysUser::getId, userIds);
        userService.remove(queryWrapper);
    }

    @GetMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-获取所有角色接口")
    @LogAnnotation(title = "用户管理", action = "赋予角色-获取所有角色接口")
    @SaCheckPermission("sys:user:role:detail")
    public UserOwnRoleRespVO getUserOwnRole(@PathVariable("userId") String userId) {
        DataResult result = DataResult.success();
        return userService.getUserOwnRole(userId);
    }

    @PutMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-用户赋予角色接口")
    @LogAnnotation(title = "用户管理", action = "赋予角色-用户赋予角色接口")
    @SaCheckPermission("sys:user:role:update")
    public void setUserOwnRole(@PathVariable("userId") String userId, @RequestBody List<String> roleIds) {

        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        userRoleService.remove(queryWrapper);
        if (!CollectionUtils.isEmpty(roleIds)) {
            UserRoleOperationReqVO reqVO = new UserRoleOperationReqVO();
            reqVO.setUserId(userId);
            reqVO.setRoleIds(roleIds);
            userRoleService.addUserRoleInfo(reqVO);
        }
    }
}
