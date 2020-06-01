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

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * 系统繁忙，请稍候再试"
     *
     * @throws
     */
    @ExceptionHandler(Exception.class)
    public <T> DataResult<T> handleException(Exception e) {
        log.error("Exception,exception:{}", e);
        return DataResult.getResult(BaseResponseCode.SYSTEM_BUSY);
    }

    /**
     * 自定义全局异常处理
     *
     * @throws
     */
    @ExceptionHandler(value = BusinessException.class)
    <T> DataResult<T> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException,exception:{}", e);
        return new DataResult<>(e.getMessageCode(), e.getDetailMessage());
    }

    /**
     * 没有权限 返回403视图
     *
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public <T> DataResult<T> erroPermission(AuthorizationException e) {
        log.error("BusinessException,exception:{}", e);
        return new DataResult<>(BaseResponseCode.UNAUTHORIZED_ERROR);

    }

    /**
     * 处理validation 框架异常
     *
     * @throws
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    <T> DataResult<T> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return createValidExceptionResp(errors);
    }

    private <T> DataResult<T> createValidExceptionResp(List<ObjectError> errors) {
        String[] msgs = new String[errors.size()];
        int i = 0;
        for (ObjectError error : errors) {
            msgs[i] = error.getDefaultMessage();
            log.info("msg={}", msgs[i]);
            i++;
        }
        return DataResult.getResult(BaseResponseCode.METHODARGUMENTNOTVALIDEXCEPTION.getCode(), msgs[0]);
    }


}
