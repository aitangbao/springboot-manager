package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.entity.SysPermission;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.mapper.SysPermissionMapper;
import com.company.project.service.*;
import com.company.project.vo.resp.PermissionRespNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements PermissionService {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private HttpSessionService httpSessionService;


    /**
     * 根据用户查询拥有的权限
     * 先查出用户拥有的角色
     * 再去差用户拥有的权限
     * 也可以多表关联查询
     */
    @Override
    public List<SysPermission> getPermission(String userId) {
        List<String> roleIds = userRoleService.getRoleIdsByUserId(userId);
        if (roleIds.isEmpty()) {
            return null;
        }
        List<String> permissionIds = rolePermissionService.getPermissionIdsByRoles(roleIds);
        if (permissionIds.isEmpty()) {
            return null;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("id", permissionIds);
        queryWrapper.orderByAsc("order_num");
        List<SysPermission> result = sysPermissionMapper.selectList(queryWrapper);
        return result;
    }

    /**
     * 新增菜单权限
     */
    @Override
    public SysPermission addPermission(SysPermission sysPermission) {
        verifyForm(sysPermission);
        sysPermission.setCreateTime(new Date());
        int count = sysPermissionMapper.insert(sysPermission);
        if (count != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        return sysPermission;
    }

    /**
     * 操作后的菜单类型是目录的时候 父级必须为目录
     * 操作后的菜单类型是菜单的时候，父类必须为目录类型
     * 操作后的菜单类型是按钮的时候 父类必须为菜单类型
     */
    private void verifyFormPid(SysPermission sysPermission) {
        SysPermission parent = sysPermissionMapper.selectById(sysPermission.getPid());
        switch (sysPermission.getType()) {
            case 1:
                if (parent != null) {
                    if (parent.getType() != 1) {
                        throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                    }
                } else if (!sysPermission.getPid().equals("0")) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                }
                break;
            case 2:
                if (parent == null || parent.getType() != 1) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_MENU_ERROR);
                }
                if (StringUtils.isEmpty(sysPermission.getUrl())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }

                break;
            case 3:
                if (parent == null || parent.getType() != 2) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_BTN_ERROR);
                }
                if (StringUtils.isEmpty(sysPermission.getPerms())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_PERMS_NULL);
                }
                if (StringUtils.isEmpty(sysPermission.getUrl())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }
                break;
        }
    }

    /**
     * 编辑或者新增的时候检验
     */
    private void verifyForm(SysPermission sysPermission) {

        verifyFormPid(sysPermission);
        /**
         * id 不为空说明是编辑
         */
        if (!StringUtils.isEmpty(sysPermission.getId())) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("pid", sysPermission.getId());
            List<SysPermission> list = sysPermissionMapper.selectList(queryWrapper);
            if (!list.isEmpty()) {
                throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_UPDATE);
            }
        }

    }

    /**
     * 查询菜单权限详情
     */
    @Override
    public SysPermission detailInfo(String permissionId) {

        return sysPermissionMapper.selectById(permissionId);
    }

    /**
     * 更新菜单权限
     */
    @Override
    public void updatePermission(SysPermission update) {

        SysPermission sysPermission = sysPermissionMapper.selectById(update.getId());
        if (null == sysPermission) {
            log.error("传入 的 id:{}不合法", update.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        /**
         * 只有类型变更
         * 或者所属菜单变更
         */
        if (sysPermission.getType().equals(update.getType()) || !sysPermission.getPid().equals(update.getPid())) {
            verifyForm(update);
        }
        update.setUpdateTime(new Date());
        int count = sysPermissionMapper.updateById(update);
        if (count != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        /**
         * 所有管理这个菜单权限用户将重新刷新token
         */
        if (StringUtils.isEmpty(update.getPerms()) && !sysPermission.getPerms().equals(update.getPerms())) {
            //刷新权限
            httpSessionService.refreshPermission(update.getId());
        }

    }

    /**
     * 删除菜单权限
     * 判断是否 有角色关联
     * 判断是否有子集
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleted(String permissionId) {
        SysPermission sysPermission = sysPermissionMapper.selectById(permissionId);
        if (null == sysPermission) {
            log.error("传入 的 id:{}不合法", permissionId);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("pid", permissionId);
        List<SysPermission> childs = sysPermissionMapper.selectList(queryWrapper);
        if (!childs.isEmpty()) {
            throw new BusinessException(BaseResponseCode.ROLE_PERMISSION_RELATION);
        }
        int count = sysPermissionMapper.deleteById(permissionId);
        if (count != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        //删除和角色关联
        rolePermissionService.removeByPermissionId(permissionId);
        //刷新权限
        httpSessionService.refreshPermission(permissionId);
    }


    /**
     * 获取所有菜单权限
     */
    @Override
    public List<SysPermission> selectAll() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("order_num");
        List<SysPermission> result = sysPermissionMapper.selectList(queryWrapper);
        if (!result.isEmpty()) {
            for (SysPermission sysPermission : result) {
                SysPermission parent = sysPermissionMapper.selectById(sysPermission.getPid());
                if (parent != null) {
                    sysPermission.setPidName(parent.getName());
                }
            }
        }
        return result;
    }

    /**
     * 获取权限标识
     */
    @Override
    public Set<String> getPermissionsByUserId(String userId) {

        List<SysPermission> list = getPermission(userId);
        Set<String> permissions = new HashSet<>();
        if (null == list || list.isEmpty()) {
            return null;
        }
        for (SysPermission sysPermission : list) {
            if (!StringUtils.isEmpty(sysPermission.getPerms())) {
                permissions.add(sysPermission.getPerms());
            }

        }
        return permissions;
    }

    /**
     * 以树型的形式把用户拥有的菜单权限返回给客户端
     */
    @Override
    public List<PermissionRespNode> permissionTreeList(String userId) {
        List<SysPermission> list = getPermission(userId);
        return getTree(list, true);
    }

    /**
     * 递归获取菜单树
     */
    private List<PermissionRespNode> getTree(List<SysPermission> all, boolean type) {

        List<PermissionRespNode> list = new ArrayList<>();
        if (all == null || all.isEmpty()) {
            return list;
        }
        for (SysPermission sysPermission : all) {
            if (sysPermission.getPid().equals("0")) {
                PermissionRespNode permissionRespNode = new PermissionRespNode();
                BeanUtils.copyProperties(sysPermission, permissionRespNode);
                permissionRespNode.setTitle(sysPermission.getName());

                if (type) {
                    permissionRespNode.setChildren(getChildExcBtn(sysPermission.getId(), all));
                } else {
                    permissionRespNode.setChildren(getChildAll(sysPermission.getId(), all));
                }
                list.add(permissionRespNode);
            }
        }
        return list;
    }

    /**
     * 递归遍历所有
     */
    private List<PermissionRespNode> getChildAll(String id, List<SysPermission> all) {

        List<PermissionRespNode> list = new ArrayList<>();
        for (SysPermission sysPermission : all) {
            if (sysPermission.getPid().equals(id)) {
                PermissionRespNode permissionRespNode = new PermissionRespNode();
                BeanUtils.copyProperties(sysPermission, permissionRespNode);
                permissionRespNode.setTitle(sysPermission.getName());
                permissionRespNode.setChildren(getChildAll(sysPermission.getId(), all));
                list.add(permissionRespNode);
            }
        }
        return list;
    }

    /**
     * 只递归获取目录和菜单
     */
    private List<PermissionRespNode> getChildExcBtn(String id, List<SysPermission> all) {

        List<PermissionRespNode> list = new ArrayList<>();
        for (SysPermission sysPermission : all) {
            if (sysPermission.getPid().equals(id) && sysPermission.getType() != 3) {
                PermissionRespNode permissionRespNode = new PermissionRespNode();
                BeanUtils.copyProperties(sysPermission, permissionRespNode);
                permissionRespNode.setTitle(sysPermission.getName());
                permissionRespNode.setChildren(getChildExcBtn(sysPermission.getId(), all));
                list.add(permissionRespNode);
            }
        }
        return list;
    }

    /**
     * 获取所有菜单权限按钮
     */
    @Override
    public List<PermissionRespNode> selectAllByTree() {

        List<SysPermission> list = selectAll();
        return getTree(list, false);
    }

    /**
     * 获取所有的目录菜单树排除按钮
     * 因为不管是新增或者修改
     * 选择所属菜单目录的时候
     * 都不可能选择到按钮
     * 而且编辑的时候 所属目录不能
     * 选择自己和它的子类
     */
    @Override
    public List<PermissionRespNode> selectAllMenuByTree(String permissionId) {

        List<SysPermission> list = selectAll();
        if (!list.isEmpty() && !StringUtils.isEmpty(permissionId)) {
            for (SysPermission sysPermission : list) {
                if (sysPermission.getId().equals(permissionId)) {
                    list.remove(sysPermission);
                    break;
                }
            }
        }
        List<PermissionRespNode> result = new ArrayList<>();
        //新增顶级目录是为了方便添加一级目录
        PermissionRespNode respNode = new PermissionRespNode();
        respNode.setId("0");
        respNode.setTitle("默认顶级菜单");
        respNode.setSpread(true);
        respNode.setChildren(getTree(list, true));
        result.add(respNode);
        return result;
    }
}
