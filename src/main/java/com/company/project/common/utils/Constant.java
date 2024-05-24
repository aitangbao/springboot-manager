package com.company.project.common.utils;

/**
 * Constant
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public class Constant {

    /**
     * 未删除值
     */
    public static final Integer DATA_NOT_DELETED = 1;

    /**
     * 数据库类型
     */
    public static final String DB_TYPE_MYSQL = "mysql";
    public static final String DB_TYPE_ORACLE = "oracle";
    public static final String DB_TYPE_SQL_SERVER = "sqlServer";

    /**
     * 定时任务状态
     */
    public static final Integer SCHEDULER_STATUS_NORMAL = 0;
    public static final Integer SCHEDULER_STATUS_PAUSE = 1;

    /**
     * 数据范围类型 1:所有/2:自定义/3:本部门及一下/4:仅本部门/5:自己
     */
    public static final Integer DATA_SCOPE_ALL = 1;
    public static final Integer DATA_SCOPE_CUSTOM = 2;
    public static final Integer DATA_SCOPE_DEPT_AND_CHILD = 3;
    public static final Integer DATA_SCOPE_DEPT = 4;
    public static final Integer DATA_SCOPE_DEPT_SELF = 5;
}
