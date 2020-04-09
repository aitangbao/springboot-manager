package com.company.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SysUser;
import com.company.project.vo.req.*;
import com.company.project.vo.resp.LoginRespVO;
import com.company.project.vo.resp.UserOwnRoleRespVO;

import java.util.List;

public interface UserService extends IService<SysUser> {

    String register(RegisterReqVO vo);

    LoginRespVO login(LoginReqVO vo);

    void updateUserInfo(UserUpdateReqVO vo, String operationId);


    IPage<SysUser> pageInfo(SysUser vo);

    SysUser detailInfo(String userId);

    void addUser(UserAddReqVO vo);

    void logout();

    void updatePwd(UpdatePasswordReqVO vo,String userId);

    List<SysUser> getUserListByDeptIds(List<String> deptIds);

    void deletedUsers(List<String> userIds,String operationId);

    UserOwnRoleRespVO getUserOwnRole(String userId);

    void setUserOwnRole(String userId,List<String> roleIds);

    void updateUserInfoMy(UserUpdateReqVO vo, String userId);
}
