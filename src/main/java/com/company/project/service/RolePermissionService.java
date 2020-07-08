package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SysRolePermission;
import com.company.project.vo.req.RolePermissionOperationReqVO;

/**
 * 角色权限关联
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface RolePermissionService extends IService<SysRolePermission> {

    void addRolePermission(RolePermissionOperationReqVO vo);
}
