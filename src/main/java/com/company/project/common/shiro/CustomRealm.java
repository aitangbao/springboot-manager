package com.company.project.common.shiro;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.utils.Constant;
import com.company.project.entity.SysUser;
import com.company.project.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    @Lazy
    private PermissionService permissionService;
    @Autowired
    @Lazy
    private RoleService roleService;
    @Autowired
    @Lazy
    private HttpSessionService httpSessionService;
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.permissionRefresh}")
    private String redisPermissionRefreshKey;
    @Value("${redis.key.prefix.userToken}")
    private String USER_TOKEN_PREFIX;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        JSONObject redisSession = httpSessionService.getCurrentSession();
        if (redisSession == null) {
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }

        String userId = httpSessionService.getCurrentUserId();
        //如果修改了角色/权限， 那么刷新权限
        if (redisService.exists(redisPermissionRefreshKey + userId)) {

            List<String> roleNames = getRolesByUserId(userId);
            if (roleNames != null && !roleNames.isEmpty()) {
                redisSession.put(Constant.ROLES_KEY, roleNames);
                authorizationInfo.addRoles(roleNames);
            }
            Set<String> permissions = getPermissionsByUserId(userId);
            authorizationInfo.setStringPermissions(permissions);
            redisSession.put(Constant.PERMISSIONS_KEY, permissions);

            String redisTokenKey = USER_TOKEN_PREFIX + httpSessionService.getTokenFromHeader();
            Long redisTokenKeyExpire = redisService.getExpire(redisTokenKey);
            //刷新token绑定的角色权限
            redisService.setAndExpire(redisTokenKey, redisSession.toJSONString(), redisTokenKeyExpire);
            //刷新后删除权限刷新标志
            redisService.del(redisPermissionRefreshKey + userId);
        } else {
            if (httpSessionService.getCurrentSession().get(Constant.ROLES_KEY) != null) {
                authorizationInfo.addRoles((Collection<String>) httpSessionService.getCurrentSession().get(Constant.ROLES_KEY));
            }
            if (httpSessionService.getCurrentSession().get(Constant.PERMISSIONS_KEY) != null) {
                authorizationInfo.addStringPermissions((Collection<String>) httpSessionService.getCurrentSession().get(Constant.PERMISSIONS_KEY));
            }
        }


        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("============用户验证==============");
        //从token中获取信息,此token只是shiro用于身份验证的,并非前端传过来的token.
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        SysUser sysUser = userService.getOne(queryWrapper);
        String password = sysUser.getPassword();

        if (null == password) {
            throw new AuthenticationException("doGetAuthenticationInfo中的用户名不对");
        } else if (!password.equals(new String(token.getPassword()))) {
            throw new AuthenticationException("doGetAuthenticationInfo中的密码不对");
        }
        //组合一个验证信息
        System.out.println("token.getPrincipal()默认返回的username======" + token.getPrincipal());
        System.out.println("getName()" + getName());
        SimpleAuthenticationInfo info =
                new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
        return info;
    }

    private List<String> getRolesByUserId(String userId) {
        return roleService.getRoleNames(userId);
    }

    private Set<String> getPermissionsByUserId(String userId) {
        return permissionService.getPermissionsByUserId(userId);
    }
}
