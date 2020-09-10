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
     * redis的token相关
     */
    public static final String ACCESS_TOKEN = "authorization";
    public static final String PERMISSIONS_KEY = "permissions-key";
    public static final String USERID_KEY = "userid-key";
    public static final String USERNAME_KEY = "username-key";
    public static final String ROLES_KEY = "roles-key";

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

}
