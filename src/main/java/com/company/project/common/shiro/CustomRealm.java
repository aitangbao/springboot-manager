package com.company.project.common.shiro;

import com.alibaba.fastjson.JSONObject;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.utils.Constant;
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

    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
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


    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getPrincipal(), getName());
        return simpleAuthenticationInfo;
    }

    private List<String> getRolesByUserId(String userId) {
        return roleService.getRoleNames(userId);
    }

    private Set<String> getPermissionsByUserId(String userId) {
        return permissionService.getPermissionsByUserId(userId);
    }
}
