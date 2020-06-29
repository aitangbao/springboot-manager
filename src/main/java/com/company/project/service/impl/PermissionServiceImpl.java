package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.entity.SysPermission;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.entity.SysRolePermission;
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

/**
 * 菜单权限
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
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
        LambdaQueryWrapper<SysRolePermission> sysRoleQueryWrapper = new LambdaQueryWrapper();
        sysRoleQueryWrapper.select(SysRolePermission::getPermissionId).in(SysRolePermission::getRoleId, roleIds);
        List<Object> permissionIds = rolePermissionService.listObjs(sysRoleQueryWrapper);
        if (permissionIds.isEmpty()) {
            return null;
        }
        LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(SysPermission::getId, permissionIds);
        queryWrapper.orderByAsc(SysPermission::getOrderNum);
        List<SysPermission> result = sysPermissionMapper.selectList(queryWrapper);
        return result;
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
        //获取下一级
        LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysPermission::getPid, permissionId);
        List<SysPermission> childs = sysPermissionMapper.selectList(queryWrapper);
        if (!childs.isEmpty()) {
            throw new BusinessException(BaseResponseCode.ROLE_PERMISSION_RELATION);
        }
        sysPermissionMapper.deleteById(permissionId);
        //删除和角色关联
        LambdaQueryWrapper<SysRolePermission> LambdaQueryWrapper = new LambdaQueryWrapper();
        LambdaQueryWrapper.eq(SysRolePermission::getPermissionId, permissionId);
        rolePermissionService.remove(LambdaQueryWrapper);
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
