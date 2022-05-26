package com.company.project.common.filter;

import com.alibaba.fastjson.JSON;
import com.company.project.common.utils.DataResult;
import com.company.project.service.HttpApiSessionService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.company.project.service.HttpApiSessionService.USER_ID_KEY;
import static com.company.project.service.HttpApiSessionService.USER_USERNAME_KEY;

@Slf4j
@WebFilter(filterName = "authFilter", urlPatterns = "/app/api/*")
@Order(1)
public class AuthFilter implements Filter {
    /**
     * 白名单
     */
    private static final String[] whiteList = {"/app/api/login", "/app/api/open/test"};
    @Resource
    HttpApiSessionService httpApiSessionService;
    //需要拦截的地址
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String url = req.getRequestURI();
        log.info("url:{}", url);
        if (Arrays.asList(whiteList).contains(url)) {
            chain.doFilter(request, response);
        } else {
            //拦截接口
            //从header中获取token
            String token = req.getHeader("token");
            //如果header中不存在token，则从参数中获取token
            if (StringUtils.isBlank(token)) {
                token = request.getParameter("token");
            }
            //token为空返回
            if (StringUtils.isBlank(token)) {
                responseResult(resp, DataResult.fail("token不能为空"));
            }
            //  校验并解析token，如果token过期或者篡改，则会返回null
            Claims claims = httpApiSessionService.checkJWT(token);
            if (null == claims) {
                responseResult(resp, DataResult.fail("登陆失效， 请重新登陆"));
            }
            //TODO 校验用户状态等

            //  校验通过后，设置用户信息到request里，在Controller中从Request域中获取用户信息
            request.setAttribute(USER_ID_KEY, claims.get(USER_ID_KEY));
            request.setAttribute(USER_USERNAME_KEY, claims.get(USER_USERNAME_KEY));
        }

    }

    /**
     * responseResult
     * @param response
     * @param result
     */
    private void responseResult(HttpServletResponse response, DataResult result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}