package com.company.project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.company.project.common.utils.Constant;
import com.company.project.entity.SysRolePermission;
import com.company.project.entity.SysUser;
import com.company.project.entity.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * session管理器
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
public class HttpSessionService {

    @Autowired
    private RedisService redisDB;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @Value("${spring.redis.key.prefix.userToken}")
    private String USER_TOKEN_PREFIX;

    @Value("${spring.redis.key.expire.userToken}")
    private int EXPIRE;

    @Value("${spring.redis.key.prefix.permissionRefresh}")
    private String redisPermissionRefreshKey;
    @Value("${spring.redis.key.expire.permissionRefresh}")
    private Long redisPermissionRefreshExpire;

    public String createTokenAndUser(SysUser user, List<String> roles, Set<String> permissions) {
        //方便根据id找到redis的key， 修改密码/退出登陆 方便使用
        String token = getRandomToken(32) + "#" + user.getId();
        JSONObject sessionInfo = new JSONObject();
        sessionInfo.put(Constant.USERID_KEY, user.getId());
        sessionInfo.put(Constant.USERNAME_KEY, user.getUsername());
        sessionInfo.put(Constant.ROLES_KEY, roles);
        sessionInfo.put(Constant.PERMISSIONS_KEY, permissions);
        String key = USER_TOKEN_PREFIX + token;
        //设置该用户已登录的token
        redisDB.setAndExpire(key, sessionInfo.toJSONString(), EXPIRE);

        //登陆后删除权限刷新标志
        redisDB.del(redisPermissionRefreshKey + user.getId());
        return token;
    }

    /**
     * 根据token获取userid
     *
     * @param token
     * @return
     */
    public static String getUserIdByToken(String token) {
        if (StringUtils.isBlank(token) || !token.contains("#")) {
            return "" ;
        } else {
            return token.substring(token.indexOf("#") + 1);
        }
    }

    /**
     * 获取参数中的token
     *
     * @return
     */
    public String getTokenFromHeader() {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(Constant.ACCESS_TOKEN);
        }
        return token;
    }

    /**
     * 获取当前session信息
     *
     * @return
     */
    public JSONObject getCurrentSession() {
        String token = getTokenFromHeader();
        if (null != token) {
            if (redisDB.exists(USER_TOKEN_PREFIX + token)) {
                String sessionInfoStr = redisDB.get(USER_TOKEN_PREFIX + token);
                JSONObject sessionInfo = JSON.parseObject(sessionInfoStr);
                return sessionInfo;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取当前session信息
     *
     * @return
     */
    public String getCurrentUsername() {
        if (getCurrentSession() != null) {
            return getCurrentSession().getString(Constant.USERNAME_KEY);
        } else {
            return null;
        }
    }

    /**
     * 获取当前session信息
     *
     * @return
     */
    public String getCurrentUserId() {
        if (getCurrentSession() != null) {
            return getCurrentSession().getString(Constant.USERID_KEY);
        } else {
            return null;
        }
    }


    /**
     * 使当前用户的token失效
     */
    public void abortUserByToken() {
        String token = getTokenFromHeader();
        redisDB.del(USER_TOKEN_PREFIX + token);
    }

    /**
     * 使所有用户的token失效
     */
    public void abortAllUserByToken() {
        String token = getTokenFromHeader();
        String userId = getUserIdByToken(token);
        redisDB.delKeys(USER_TOKEN_PREFIX + "*#" + userId);
    }

    /**
     * 使用户的token失效
     */
    public void abortUserById(String userId) {
        redisDB.delKeys(USER_TOKEN_PREFIX + "*#" + userId);
    }

    /**
     * 使多个用户的token失效
     */
    public void abortUserByUserIds(List<String> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            for (String id : userIds) {
                redisDB.delKeys(USER_TOKEN_PREFIX + "*#" + id);
            }

        }
    }

    /**
     * 根据用户id， 刷新redis用户权限
     *
     * @param userId
     */
    public void refreshUerId(String userId) {
        Set<String> keys = redisDB.keys("#" + userId);
        //如果修改了角色/权限， 那么刷新权限
        for (String key : keys) {
            JSONObject redisSession = JSON.parseObject(redisDB.get(key));

            List<String> roleNames = getRolesByUserId(userId);
            if (roleNames != null && !roleNames.isEmpty()) {
                redisSession.put(Constant.ROLES_KEY, roleNames);
            }
            Set<String> permissions = getPermissionsByUserId(userId);
            redisSession.put(Constant.PERMISSIONS_KEY, permissions);

            Long redisTokenKeyExpire = redisDB.getExpire(key);
            //刷新token绑定的角色权限
            redisDB.setAndExpire(key, redisSession.toJSONString(), redisTokenKeyExpire);

        }


    }

    /**
     * 根据角色id， 刷新redis用户权限
     *
     * @param roleId
     */
    public void refreshRolePermission(String roleId) {
        List<String> userIds = userRoleService.getUserIdsByRoleId(roleId);
        if (!userIds.isEmpty()) {
            for (String userId : userIds) {
                redisDB.setAndExpire(redisPermissionRefreshKey + userId, userId, redisPermissionRefreshExpire);
            }

        }
    }

    /**
     * 根据权限id， 刷新redis用户权限
     *
     * @param permissionId
     */
    public void refreshPermission(String permissionId) {
        //根据权限id，获取所有角色id
        LambdaQueryWrapper<SysRolePermission> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.select(SysRolePermission::getRoleId).eq(SysRolePermission::getPermissionId, permissionId);
        List<Object> roleIds = rolePermissionService.listObjs(queryWrapper);
        if (!roleIds.isEmpty()) {
            //根据角色id， 获取关联用户
            LambdaQueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new LambdaQueryWrapper();
            sysUserRoleQueryWrapper.select(SysUserRole::getUserId).in(SysUserRole::getRoleId, roleIds);
            List<Object> userIds = userRoleService.listObjs(sysUserRoleQueryWrapper);
            if (!userIds.isEmpty()) {
                //删除用户redis
                userIds.parallelStream().forEach(userId -> {
                    redisDB.setAndExpire(redisPermissionRefreshKey + userId, userId.toString(), redisPermissionRefreshExpire);
                });
            }
        }
    }


    /**
     * 生成随机的token
     *
     * @param length
     * @return
     */
    private String getRandomToken(int length) {
        Random random = new Random();
        StringBuilder randomStr = new StringBuilder();

        // 根据length生成相应长度的随机字符串
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num" ;

            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                randomStr.append((char) (random.nextInt(26) + temp));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                randomStr.append(String.valueOf(random.nextInt(10)));
            }
        }

        return randomStr.toString();
    }


    private List<String> getRolesByUserId(String userId) {
        return roleService.getRoleNames(userId);
    }

    private Set<String> getPermissionsByUserId(String userId) {
        return permissionService.getPermissionsByUserId(userId);
    }

}
