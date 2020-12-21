package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SysRole;

import java.util.List;

/**
 * 角色
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface RoleService extends IService<SysRole> {

    /**
     * 添加角色
     *
     * @param vo SysRole
     */
    void addRole(SysRole vo);

    /**
     * 更新角色
     *
     * @param vo SysRole
     */
    void updateRole(SysRole vo);

    /**
     * 根据id获取角色详情
     *
     * @param id id
     * @return SysRole
     */
    SysRole detailInfo(String id);

    /**
     * 根据id删除
     *
     * @param id id
     */
    void deletedRole(String id);

    /**
     * 根据userId获取绑定的角色
     *
     * @param userId userId
     * @return List
     */
    List<SysRole> getRoleInfoByUserId(String userId);

    /**
     * 根据userId获取绑定的角色名
     *
     * @param userId userId
     * @return List
     */
    List<String> getRoleNames(String userId);
}
