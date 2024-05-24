package com.company.project.common.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import com.company.project.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SaToken自定义权限加载接口实现类
 * @author wenbin
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    PermissionService permissionService;

    /** 返回一个账号所拥有的权限码集合  */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        if(loginType.equals(StpUtil.TYPE)) {
            return permissionService.getPermissionsByUserId(String.valueOf(loginId));
        }
        return null;
    }

    /** 返回一个账号所拥有的角色标识集合  */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return null;
    }

}
