package com.company.project.common.utils;


import com.company.project.common.exception.BusinessException;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 *
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws BusinessException  校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws BusinessException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
        	ConstraintViolation<Object> constraint = (ConstraintViolation<Object>)constraintViolations.iterator().next();
            throw new BusinessException(constraint.getMessage());
        }
    }

    /**
     * 空判断处理
     * @param str
     * @param message
     */
    public static void isBlank(Object str, String message) {
        if (str == null) {
            throw new BusinessException(message);
        }
        if (str instanceof String) {
            if (StringUtils.isBlank(String.valueOf(str))) {
                throw new BusinessException(message);
            }
        }
    }
}
