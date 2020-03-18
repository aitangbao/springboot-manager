package com.company.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.company.project.entity.SysRole;
import com.company.project.vo.req.RoleAddReqVO;
import com.company.project.vo.req.RolePageReqVO;
import com.company.project.vo.req.RoleUpdateReqVO;

import java.util.List;

public interface RoleService {

    SysRole addRole(RoleAddReqVO vo);

    void updateRole(RoleUpdateReqVO vo, String accessToken);

    SysRole detailInfo(String id);

    void deletedRole(String id);

    IPage<SysRole> pageInfo(RolePageReqVO vo);

    List<SysRole> getRoleInfoByUserId(String userId);

    List<String> getRoleNames(String userId);

    List<SysRole> selectAllRoles();
}
