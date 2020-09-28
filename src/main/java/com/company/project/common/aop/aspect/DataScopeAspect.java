package com.company.project.common.aop.aspect;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.entity.*;
import com.company.project.service.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 1、首先配置角色的数据范围（本部门，其他部门等）， 以及绑定的部门
 * 2、加个注解，用来查询当前等路人的多个角色， 根据角色数据范围， 获取绑定的部门id， 查关联的用户id
 * 3、在查某个模块的list或page的时候，手动queryWrapper.in(createId, 关联的用户id)
 *
 * @author wenbin
 */
@Aspect
@Component
public class DataScopeAspect {

    @Resource
    HttpSessionService sessionService;
    @Resource
    RoleService roleService;
    @Resource
    SysRoleDeptService sysRoleDeptService;
    @Resource
    DeptService deptService;
    @Resource
    UserService userService;
    //自定数据权限
    public static final Integer DATA_SCOPE_CUSTOM = 1;
    //部门及以下数据权限
    public static final Integer DATA_SCOPE_DEPT_AND_CHILD = 2;

    // 配置织入点
    @Pointcut("@annotation(com.company.project.common.aop.annotation.DataScopeAnnotation)")
    public void dataScopePointCut() {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) {
        handleDataScope(point);
    }

    protected void handleDataScope(final JoinPoint joinPoint) {
        //获取当前登陆人
        String id = sessionService.getCurrentUserId();
        //获取当前登陆人角色
        List<SysRole> sysRoles = roleService.getRoleInfoByUserId(id);
        if (CollectionUtils.isEmpty(sysRoles) || sysRoles.size() == 0) {
            return;
        }
        List<String> userIds = this.getUserIdsByRoles(sysRoles);
        if (userIds == null) {
            return;
        }
        Object params = joinPoint.getArgs()[0];
        if (params instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.setUserIds(userIds);
        }

    }

    //获取最终的用户id
    private List<String> getUserIdsByRoles(List<SysRole> sysRoles) {
        //部门id
        List<Object> list = new ArrayList<Object>();
        //如果角色范围是自定义数据权限， 应该直接就是角色直接绑定的部门
        List<String> sysRoleIds1 = sysRoles.parallelStream().filter(one -> DATA_SCOPE_CUSTOM.equals(one.getDataScope())).map(SysRole::getId).collect(Collectors.toList());
        //获取角色绑定的权限部门
        if (!CollectionUtils.isEmpty(sysRoleIds1) && sysRoleIds1.size() > 0) {
            list.add(sysRoleDeptService.listObjs(Wrappers.<SysRoleDeptEntity>lambdaQuery().select(SysRoleDeptEntity::getDeptId).in(SysRoleDeptEntity::getRoleId, sysRoleIds1)));
        }
        //如果角色范围是部门及以下数据权限数据权限
        List<String> sysRoleIds2 = sysRoles.parallelStream().filter(one -> DATA_SCOPE_DEPT_AND_CHILD.equals(one.getDataScope())).map(SysRole::getId).collect(Collectors.toList());
        //获取角色绑定的权限部门
        if (!CollectionUtils.isEmpty(sysRoleIds2) && sysRoleIds2.size() > 0) {
            List deptIds = sysRoleDeptService.listObjs(Wrappers.<SysRoleDeptEntity>lambdaQuery().select(SysRoleDeptEntity::getDeptId).in(SysRoleDeptEntity::getRoleId, sysRoleIds2));
            List<SysDept> deptList = deptService.listByIds(deptIds);
            deptList.parallelStream().forEach(one -> {
                list.add(deptService.listObjs(Wrappers.<SysDept>lambdaQuery().select(SysDept::getId).like(SysDept::getRelationCode, one.getDeptNo())));
            });
        }
        Optional<List<Object>> userIdsOption = Optional.ofNullable(list).map(deptIds -> userService.listObjs(Wrappers.<SysUser>lambdaQuery().select(SysUser::getId).in(SysUser::getDeptId, deptIds)));
        return userIdsOption.map(objects -> objects.parallelStream().map(Object::toString).collect(Collectors.toList())).orElse(null);
    }
}
