package com.company.project.common.config;

import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.utils.DataResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * 后置拦截 统一响应数据
 *
 * @description
 */
@RestControllerAdvice(basePackages = {"com.company.project.controller"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    private HttpServletRequest req;

    public ResponseControllerAdvice(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是ResultVO那就没有必要进行额外的操作，返回false
        return !returnType.getParameterType().equals(DataResult.class);
    }


    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
                // 将数据包装在Result里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString(DataResult.success(data));
            } catch (JsonProcessingException e) {
                return DataResult.fail(BaseResponseCode.ILLEGAL_DATA.getMsg());
            }
        }

        // 如果返回的是 List 类型，且为 null，则返回一个空数组
        if (data == null && List.class.isAssignableFrom(returnType.getParameterType())) {
            return DataResult.success(Collections.emptyList());
        }

        // 将原本的数据包装在Result里
        return DataResult.success(data);
    }
}
