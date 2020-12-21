package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SysPermission;
import com.company.project.vo.resp.PermissionRespNode;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface PermissionService extends IService<SysPermission> {

    /**
     * 根据userId获取权限
     *
     * @param userId userId
     * @return 权限
     */
    List<SysPermission> getPermission(String userId);

    /**
     * 删除权限
     *
     * @param permissionId 权限id
     */
    void deleted(String permissionId);

    /**
     * 获取所有
     *
     * @return List
     */
    List<SysPermission> selectAll();

    /**
     * 根据userId获取权限标志
     *
     * @param userId userId
     * @return Set
     */
    Set<String> getPermissionsByUserId(String userId);

    /**
     * 根据userId获取权限树
     *
     * @param userId
     * @return List
     */
    List<PermissionRespNode> permissionTreeList(String userId);

    /**
     * 根据权限树
     *
     * @return List
     */
    List<PermissionRespNode> selectAllByTree();

    /**
     * 根据目录树
     *
     * @param permissionId permissionId
     * @return List
     */
    List<PermissionRespNode> selectAllMenuByTree(String permissionId);


    /**
     * 根据权限id获取绑定的userId
     *
     * @param permissionId permissionId
     * @return List
     */
    List<String> getUserIdsById(String permissionId);

    /**
     * 更新
     *
     * @param vo vo
     */
    void updatePermission(SysPermission vo);
}
