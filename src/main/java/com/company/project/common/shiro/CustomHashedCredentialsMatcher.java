package com.company.project.common.shiro;

import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.service.RedisService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;

/**
 * 认证
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public class CustomHashedCredentialsMatcher extends SimpleCredentialsMatcher {

    @Lazy
    @Autowired
    private RedisService redisDb;
    @Value("${spring.redis.key.prefix.userToken}")
    private String userTokenPrefix;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String accessToken = (String) token.getPrincipal();
        if (!redisDb.exists(userTokenPrefix + accessToken)) {
            SecurityUtils.getSubject().logout();
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        return true;
    }
}
