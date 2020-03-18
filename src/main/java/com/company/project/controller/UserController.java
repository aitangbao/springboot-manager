package com.company.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.company.project.service.HttpSessionService;
import com.company.project.vo.req.*;
import com.company.project.vo.resp.LoginRespVO;
import com.company.project.vo.resp.UserOwnRoleRespVO;
import com.company.project.aop.annotation.LogAnnotation;
import com.company.project.entity.SysUser;
import com.company.project.exception.code.BaseResponseCode;
import com.company.project.service.UserService;
import com.company.project.utils.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "组织模块-用户管理")
@RequestMapping("/sys")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSessionService httpSessionService;



    @PostMapping(value = "/user/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult<LoginRespVO> login(@RequestBody @Valid LoginReqVO vo){
        DataResult<LoginRespVO> result=DataResult.success();
        result.setData(userService.login(vo));
        return result;
    }

    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册接口")
    public DataResult<String> register(@RequestBody @Valid RegisterReqVO vo){
        DataResult<String> result=DataResult.success();
        result.setData(userService.register(vo));
        return result;
    }


    @GetMapping("/user/unLogin")
    @ApiOperation(value = "引导客户端去登录")
    public DataResult unLogin(){
        DataResult result= DataResult.getResult(BaseResponseCode.TOKEN_ERROR);
        return result;
    }

    @PutMapping("/user")
    @ApiOperation(value = "更新用户信息接口")
    @LogAnnotation(title = "用户管理",action = "更新用户信息")
    @RequiresPermissions("sys:user:update")
    public DataResult updateUserInfo(@RequestBody @Valid UserUpdateReqVO vo, HttpServletRequest request){
        String userId = httpSessionService.getCurrentUserId();
        userService.updateUserInfo(vo,userId);
        return DataResult.success();
    }
    @PutMapping("/user/info")
    @ApiOperation(value = "更新用户信息接口")
    @LogAnnotation(title = "用户管理",action = "更新用户信息")
    public DataResult updateUserInfoById(@RequestBody @Valid UserUpdateReqVO vo,HttpServletRequest request){
        String userId = httpSessionService.getCurrentUserId();
        vo.setId(userId);
        userService.updateUserInfo(vo,userId);
        return DataResult.success();
    }
    @GetMapping("/user/{id}")
    @ApiOperation(value = "查询用户详情接口")
    @LogAnnotation(title = "用户管理",action = "查询用户详情")
    @RequiresPermissions("sys:user:detail")
    public DataResult<SysUser> detailInfo(@PathVariable("id") String id){
        DataResult<SysUser> result=DataResult.success();
        result.setData(userService.detailInfo(id));
        return result;
    }
    @GetMapping("/user")
    @ApiOperation(value = "查询用户详情接口")
    @LogAnnotation(title = "用户管理",action = "查询用户详情")
    public DataResult<SysUser> youSelfInfo(HttpServletRequest request){
        String userId = httpSessionService.getCurrentUserId();
        DataResult<SysUser> result=DataResult.success();
        result.setData(userService.detailInfo(userId));
        return result;
    }
    @PostMapping("/users")
    @ApiOperation(value = "分页获取用户列表接口")
    @RequiresPermissions("sys:user:list")
    @LogAnnotation(title = "用户管理",action = "分页获取用户列表")
    public DataResult<IPage<SysUser>> pageInfo(@RequestBody UserPageReqVO vo){
        return DataResult.success(userService.pageInfo(vo));
    }
    @PostMapping("/user")
    @ApiOperation(value = "新增用户接口")
    @RequiresPermissions("sys:user:add")
    @LogAnnotation(title = "用户管理",action = "新增用户")
    public DataResult addUser(@RequestBody @Valid UserAddReqVO vo){
        userService.addUser(vo);
        return DataResult.success();
    }

    @GetMapping("/user/logout")
    @ApiOperation(value = "退出接口")
    @LogAnnotation(title = "用户管理",action = "退出")
    public DataResult logout(HttpServletRequest request){
        userService.logout();
        return DataResult.success();
    }

    @PutMapping("/user/pwd")
    @ApiOperation(value = "修改密码接口")
    @LogAnnotation(title = "用户管理",action = "更新密码")
    public DataResult updatePwd(@RequestBody UpdatePasswordReqVO vo,HttpServletRequest request){
        String userId = httpSessionService.getCurrentUserId();
        userService.updatePwd(vo,userId);
        return DataResult.success();
    }

    @DeleteMapping("/user")
    @ApiOperation(value = "删除用户接口")
    @LogAnnotation(title = "用户管理",action = "删除用户")
    @RequiresPermissions("sys:user:deleted")
    public DataResult deletedUser(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds, HttpServletRequest request){
        String userId = httpSessionService.getCurrentUserId();
        userService.deletedUsers(userIds,userId);
        return DataResult.success();
    }
    @GetMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-获取所有角色接口")
    @LogAnnotation(title = "用户管理",action = "赋予角色-获取所有角色接口")
    @RequiresPermissions("sys:user:role:detail")
    public DataResult<UserOwnRoleRespVO> getUserOwnRole(@PathVariable("userId")String userId){
        DataResult<UserOwnRoleRespVO> result=DataResult.success();
       result.setData(userService.getUserOwnRole(userId));
        return result;
    }
    @PutMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-用户赋予角色接口")
    @LogAnnotation(title = "用户管理",action = "赋予角色-用户赋予角色接口")
    @RequiresPermissions("sys:user:update:role")
    public DataResult<UserOwnRoleRespVO> setUserOwnRole(@PathVariable("userId")String userId, @RequestBody List<String> roleIds){
        DataResult result=DataResult.success();
        userService.setUserOwnRole(userId,roleIds);
        return result;
    }
}
