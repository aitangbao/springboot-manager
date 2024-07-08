package com.company.project.common.filter;

import com.alibaba.fastjson.JSON;
import com.company.project.common.utils.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


@Slf4j
@WebFilter(filterName = "authFilter", urlPatterns = "/app/api/*")
@Order(1)
public class AuthFilter implements Filter {
    /**
     * 白名单
     */
    private static final String[] whiteList = {"/app/api/login", "/app/api/open/test"};

    //需要拦截的地址
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        String url = req.getRequestURI();
        log.info("url:{}", url);
        if (Arrays.asList(whiteList).contains(url)) {
            chain.doFilter(request, response);
        } else {
            //拦截接口
            //从header中获取token
            String token = req.getHeader("satoken");
            //如果header中不存在token，则从参数中获取token
            if (StringUtils.isBlank(token)) {
                token = request.getParameter("satoken");
            }
            //token为空返回
            if (StringUtils.isBlank(token)) {
                responseResult(resp, DataResult.fail("token不能为空"));
            }
        }

    }

    /**
     * responseResult
     *
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