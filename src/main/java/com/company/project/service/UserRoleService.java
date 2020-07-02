package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SysUserRole;
import com.company.project.vo.req.UserRoleOperationReqVO;

import java.util.List;

/**
 * 用户角色 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface UserRoleService  extends IService<SysUserRole> {

    List<String> getRoleIdsByUserId(String userId);

    void addUserRoleInfo(UserRoleOperationReqVO vo);

    List<String> getUserIdsByRoleId(String roleId);
}
