package com.company.project.common.aop.aspect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.utils.Constant;
import com.company.project.entity.*;
import com.company.project.service.*;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 概念：最终控制列表中显示哪些 部门/人 创建的数据
 * 1、需要数据权限控制的列表， 需要有创建人字段， 示例：文章管理
 * 2、配置角色的数据范围（本部门，其他部门等）， 以及绑定的部门
 * 3、加个注解，用来查询当前等路人的多个角色（并集）， 根据角色数据范围， 获取绑定的部门id， 查关联的用户id
 * 4、在查某个模块的list或page的时候，手动queryWrapper.in(createId, 关联的用户id)
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


    @Pointcut("@annotation(com.company.project.common.aop.annotation.DataScope)")
    public void dataScopePointCut() {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) {
        handleDataScope(point);
    }

    protected void handleDataScope(final JoinPoint joinPoint) {
        //获取当前登陆人
        String id = sessionService.getCurrentUserId();
        //获取当前登陆人角色, 如果无角色, 那么不限制
        List<SysRole> sysRoles = roleService.getRoleInfoByUserId(id);
        if (CollectionUtils.isEmpty(sysRoles) || sysRoles.size() == 0) {
            return;
        }
        //角色未配置数据权限范围, 那么不限制
        List<SysRole> list = sysRoles.parallelStream().filter(one -> null != one.getDataScope()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list) || list.size() == 0) {
            return;
        }
        //如果存在某角色配置了全部范围， 那么不限制
        if (list.stream().anyMatch(sysRole -> Constant.DATA_SCOPE_ALL.equals(sysRole.getDataScope()))) {
            return;
        }
        //获取绑定的人
        List<String> userIds = this.getUserIdsByRoles(list, id);
        Object params = joinPoint.getArgs()[0];
        if (params instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.setCreateIds(userIds);
        }

    }

    /**
     * 获取最终的用户id
     *
     * @param sysRoles 角色
     * @param userId   当前用户id
     * @return 用户id集合
     */
    private List<String> getUserIdsByRoles(List<SysRole> sysRoles, String userId) {
        //本人
        SysUser sysUser = userService.getById(userId);
        //本部门
        SysDept sysDept = deptService.getById(sysUser.getDeptId());
        //部门ids， 定义哪些部门最终拥有权限查看
        LinkedList<Object> deptList = new LinkedList<>();
        //用户ids，定义列表中哪些人创建的可查看
        LinkedList<Object> userIdList = new LinkedList<>();
        //根据数据权限范围分组， 不同的数据范围不同的逻辑处理
        Map<Integer, List<SysRole>> dataScopeMap = sysRoles.parallelStream().collect(Collectors.groupingBy(SysRole::getDataScope));
        dataScopeMap.forEach((k, v) -> {
            if (Constant.DATA_SCOPE_CUSTOM.equals(k)) {
                //自定义
                //根据角色id，获取所有自定义关联的部门id
                QueryWrapper<SysRoleDeptEntity> queryWrapper = Wrappers.<SysRoleDeptEntity>query().select("dept_id").in("role_id", v.parallelStream().map(SysRole::getId).collect(Collectors.toList()));
                deptList.addAll(sysRoleDeptService.listObjs(queryWrapper));
            } else if (Constant.DATA_SCOPE_DEPT_AND_CHILD.equals(k)) {
                //本部门及以下
                if (sysDept != null && StringUtils.isNotBlank(sysDept.getDeptNo())) {
                    //获取本部门以下所有关联的部门
                    QueryWrapper<SysDept> queryWrapper = Wrappers.<SysDept>query().select("id").like("relation_code", sysDept.getDeptNo());
                    deptList.addAll(deptService.listObjs(queryWrapper));
                }
            } else if (Constant.DATA_SCOPE_DEPT.equals(k)) {
                //本部门
                if (sysDept != null && StringUtils.isNotBlank(sysDept.getId())) {
                    deptList.add(sysDept.getId());
                }
            } else if (Constant.DATA_SCOPE_DEPT_SELF.equals(k)) {
                //自己
                userIdList.add(userId);
            }
        });
        if (!CollectionUtils.isEmpty(deptList)) {
            QueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>query().select("id").in("dept_id", deptList);
            userIdList.addAll(userService.listObjs(queryWrapper));
        }
        //如果配置了角色数据范围， 最终没有查到userId， 那么返回无数据
        if (CollectionUtils.isEmpty(userIdList)) {
            throw new BusinessException("无数据");
        }
        return userIdList.parallelStream().map(Object::toString).collect(Collectors.toList());
    }
}
