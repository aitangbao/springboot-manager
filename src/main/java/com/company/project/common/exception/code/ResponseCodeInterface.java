package com.company.project.common.exception.code;

/**
 * ResponseCodeInterface
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface ResponseCodeInterface {
    /**
     * 获取code
     */
    int getCode();

    /**
     * 获取信息
     */
    String getMsg();
}
