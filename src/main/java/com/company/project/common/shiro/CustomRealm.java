package com.company.project.common.shiro;

import com.alibaba.fastjson.JSON;
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
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 授权
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Lazy
    @Autowired
    private PermissionService permissionService;
    @Lazy
    @Autowired
    private RoleService roleService;
    @Lazy
    @Autowired
    private RedisService redisService;
    @Value("${spring.redis.key.prefix.permissionRefresh}")
    private String redisPermissionRefreshKey;
    @Value("${spring.redis.key.prefix.userToken}")
    private String userTokenPrefix;
    @Lazy
    @Autowired
    private RedisService redisDb;


    /**
     * 执行授权逻辑
     */
    @Override
    @SuppressWarnings("unchecked")
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        String sessionInfoStr = redisDb.get(userTokenPrefix + principalCollection.getPrimaryPrincipal());
        if (StringUtils.isEmpty(sessionInfoStr)) {
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        JSONObject redisSession = JSON.parseObject(sessionInfoStr);
        if (redisSession == null) {
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }

        String userId = redisSession.getString(Constant.USERID_KEY);
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

            String redisTokenKey = userTokenPrefix + principalCollection.getPrimaryPrincipal();
            Long redisTokenKeyExpire = redisService.getExpire(redisTokenKey);
            //刷新token绑定的角色权限
            redisService.setAndExpire(redisTokenKey, redisSession.toJSONString(), redisTokenKeyExpire);
            //刷新后删除权限刷新标志
            redisService.del(redisPermissionRefreshKey + userId);
        } else {
            if (redisSession.get(Constant.ROLES_KEY) != null) {
                authorizationInfo.addRoles((Collection<String>) redisSession.get(Constant.ROLES_KEY));
            }
            if (redisSession.get(Constant.PERMISSIONS_KEY) != null) {
                authorizationInfo.addStringPermissions((Collection<String>) redisSession.get(Constant.PERMISSIONS_KEY));
            }
        }


        return authorizationInfo;
    }


    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getPrincipal(), getName());
    }

    private List<String> getRolesByUserId(String userId) {
        return roleService.getRoleNames(userId);
    }

    private Set<String> getPermissionsByUserId(String userId) {
        return permissionService.getPermissionsByUserId(userId);
    }
}
