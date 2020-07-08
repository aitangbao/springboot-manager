package com.company.project.common.exception.handler;

import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.utils.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestExceptionHandler
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * 系统繁忙，请稍候再试"
     */
    @ExceptionHandler(Exception.class)
    public DataResult handleException(Exception e) {
        log.error("Exception,exception:{}", e, e);
        return DataResult.getResult(BaseResponseCode.SYSTEM_BUSY);
    }

    /**
     * 自定义全局异常处理
     */
    @ExceptionHandler(value = BusinessException.class)
    DataResult businessExceptionHandler(BusinessException e) {
        log.error("Exception,exception:{}", e, e);
        return new DataResult(e.getMessageCode(), e.getDetailMessage());
    }

    /**
     * 没有权限 返回403视图
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public DataResult errorPermission(AuthorizationException e) {
        log.error("Exception,exception:{}", e, e);
        return new DataResult(BaseResponseCode.UNAUTHORIZED_ERROR);

    }

    /**
     * 处理validation 框架异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    DataResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return DataResult.getResult(BaseResponseCode.METHODARGUMENTNOTVALIDEXCEPTION.getCode(), errors.get(0).getDefaultMessage());
    }

}
