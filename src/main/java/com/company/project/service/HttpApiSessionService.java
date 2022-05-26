package com.company.project.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * session管理器
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年5月11日
 */
@Service
public class HttpApiSessionService {


    public static final String USER_ID_KEY = "user_id_key";

    public static final String USER_USERNAME_KEY = "user_username_key";

    public static final String SUBJECT = "onehee";

    public static final long EXPIRE = 1000 * 60 * 60 * 24 * 30;  //过期时间，毫秒，一个月

    //秘钥
    public static final String APPSECRET = "onehee666";

    @Resource
    private HttpServletRequest request;

    /**
     * 生成jwt
     *
     * @param userId username
     * @return
     */
    public String geneJsonWebToken(String userId, String userName) {
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim(USER_ID_KEY, userId)
                .claim(USER_USERNAME_KEY, userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();

        return token;
    }


    /**
     * 校验token
     *
     * @param token
     * @return
     */
    public Claims checkJWT(String token) {

        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET).
                    parseClaimsJws(token).getBody();
            return claims;

        } catch (Exception e) {
        }
        return null;

    }

    /**
     * 获取当前session信息 username
     *
     * @return username
     */
    public String getCurrentUsername() {
        if (request.getAttribute(USER_USERNAME_KEY) != null) {
            return request.getAttribute(USER_USERNAME_KEY).toString();
        } else {
            return null;
        }
    }

    /**
     * 获取当前session信息 UserId
     *
     * @return UserId
     */
    public String getCurrentUserId() {
        if (request.getAttribute(USER_ID_KEY) != null) {
            return request.getAttribute(USER_ID_KEY).toString();
        } else {
            return null;
        }
    }


}
