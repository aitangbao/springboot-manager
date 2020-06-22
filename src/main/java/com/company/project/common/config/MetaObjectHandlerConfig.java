package com.company.project.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.company.project.common.utils.Constant;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;


/**
 * mybatis plus 默认值配置
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date currentDate = new Date();
        String[] setterNames = metaObject.getSetterNames();
        HashSet<String> setterNameSet = new HashSet<>(Arrays.asList(setterNames));
        if (setterNameSet.contains("deleted")) {
            //默认未删除
            setFieldValByName("deleted", Constant.DATA_NOT_DELETED, metaObject);
        }
        if (setterNameSet.contains("createTime")) {
            //创建时间默认当前时间
            setFieldValByName("createTime", currentDate, metaObject);
        }
        if (setterNameSet.contains("createDate")) {
            //创建时间默认当前时间
            setFieldValByName("createDate", currentDate, metaObject);
        }


    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date currentDate = new Date();
        String[] setterNames = metaObject.getSetterNames();
        HashSet<String> setterNameSet = new HashSet<>(Arrays.asList(setterNames));
        if (setterNameSet.contains("updateTime")) {
            //创建时间默认当前时间
            setFieldValByName("updateTime", currentDate, metaObject);
        }
        if (setterNameSet.contains("updateDate")) {
            //创建时间默认当前时间
            setFieldValByName("updateDate", currentDate, metaObject);
        }
    }
}