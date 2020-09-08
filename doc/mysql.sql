-- ----------------------------
-- Table structure for sys_content
-- ----------------------------
DROP TABLE IF EXISTS `sys_content`;
CREATE TABLE `sys_content`  (
  `id` bigint(32) NOT NULL COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '标题',
  `type` int(11) NULL DEFAULT NULL COMMENT '文章类型',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '文章管理' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(32) NOT NULL COMMENT '主键',
  `dept_no` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门编号(规则：父级关系编码+自己的编码)',
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `pid` bigint(32) NOT NULL COMMENT '父级id',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态(1:正常；0:弃用)',
  `relation_code` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '为了维护更深层级关系',
  `dept_manager_id` bigint(32) NULL DEFAULT NULL COMMENT '部门经理user_id',
  `manager_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门经理名称',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门经理联系电话',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统部门' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(32) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典表' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail`  (
  `id` bigint(32) NOT NULL,
  `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典标签',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典值',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `dict_id` bigint(32) NULL DEFAULT NULL COMMENT '字典id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典详情' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_files
-- ----------------------------
DROP TABLE IF EXISTS `sys_files`;
CREATE TABLE `sys_files`  (
  `id` bigint(32) NOT NULL,
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件上传' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `id` bigint(32) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `id` bigint(32) NOT NULL COMMENT '任务日志id',
  `job_id` bigint(32) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `job_id`(`job_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(32) NOT NULL,
  `user_id` bigint(32) NULL DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `time` int(11) NULL DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(32) NOT NULL COMMENT '主键',
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单权限名称',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问地址URL',
  `target` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'a target属性:_self _blank',
  `pid` bigint(32) NULL DEFAULT NULL COMMENT '父级菜单权限名称',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '菜单权限类型(1:目录;2:菜单;3:按钮)',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态1:正常 0：禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统权限' ROW_FORMAT = Compact;
-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(32) NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态(1:正常0:弃用)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint(32) NOT NULL COMMENT '主键',
  `role_id` bigint(32) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(32) NULL DEFAULT NULL COMMENT '菜单权限id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(32) NOT NULL COMMENT '用户id',
  `user_id` bigint(32) NULL DEFAULT NULL,
  `role_id` bigint(32) NULL DEFAULT NULL COMMENT '角色id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户角色' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(32) NOT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户名称',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密盐值',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码密文',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `dept_id` bigint(32) NULL DEFAULT NULL COMMENT '部门id',
  `real_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实名称',
  `nick_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱(唯一)',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '账户状态(1.正常 2.锁定 )',
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '性别(1.男 2.女)',
  `deleted` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  `create_id` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint(32) NULL DEFAULT NULL COMMENT '更新人',
  `create_where` tinyint(4) NULL DEFAULT NULL COMMENT '创建来源(1.web 2.android 3.ios )',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Compact;

-- records
INSERT INTO `sys_user` VALUES (1, 'admin', '324ce32d86224b00a02b', '2102b59a75ab87616b62d0b9432569d0', '13888888888', 1, '爱糖宝', '爱糖宝', 'yingxue@163.com', 1, 2, 1, NULL, 1, 3, '2019-09-22 19:38:05', '2020-03-18 09:15:22');
INSERT INTO `sys_role` VALUES (1, '超级管理员', '拥有所有权限-不能删除', 1, '2019-11-01 19:26:29', '2020-07-31 14:04:32', 1);
INSERT INTO `sys_user_role` VALUES (1, 1, 1, '2020-03-19 02:23:13');
INSERT INTO `sys_dept` VALUES (1, 'D000001', '总公司', 0, 1, 'D000001', NULL, '小李', '13888888888', '2019-11-07 22:43:33', NULL, 1);
INSERT INTO `sys_permission` VALUES (1, '删除', 'sysGenerator:delete', NULL, 'sysGenerator/delete', NULL, 15, 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (2, 'SQL 监控', '', '', '/druid/sql.html', '_self', 21, 98, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:59', 1);
INSERT INTO `sys_permission` VALUES (3, '新增', 'sys:role:add', NULL, '/sys/role', NULL, 44, 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (4, '添加', 'sysGenerator:add', NULL, 'sysGenerator/add', NULL, 15, 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (5, '删除', 'sys:dept:deleted', NULL, '/sys/dept/*', NULL, 32, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (6, '接口管理', '', '', '/doc.html', '_blank', 21, 100, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:02', 1);
INSERT INTO `sys_permission` VALUES (7, '列表', 'sys:log:list', NULL, '/sys/logs', NULL, 8, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (8, '日志管理', NULL, NULL, '/index/logs', '_self', 45, 97, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (9, '新增', 'sys:dept:add', NULL, '/sys/dept', NULL, 32, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (10, '赋予角色', 'sys:user:role:update', NULL, '/sys/user/roles/*', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (11, '菜单权限管理', NULL, NULL, '/index/menus', '_self', 42, 98, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (12, '列表', 'sys:dept:list', NULL, '/sys/depts', NULL, 32, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (13, '删除', 'sys:role:deleted', NULL, '/sys/role/*', NULL, 44, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (14, '定时任务立即执行', 'sysJob:run', NULL, '/sysJob/run', '_self', 50, 5, 3, 1, '2020-04-22 15:47:54', NULL, 1);
INSERT INTO `sys_permission` VALUES (15, '代码生成', NULL, NULL, '/index/sysGenerator', '_self', 45, 1, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (16, '列表', 'sysGenerator:list', NULL, 'sysGenerator/listByPage', NULL, 15, 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (17, '详情', 'sys:permission:detail', NULL, '/sys/permission/*', NULL, 11, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (18, '定时任务恢复', 'sysJob:resume', NULL, '/sysJob/resume', '_self', 50, 4, 3, 1, '2020-04-22 15:48:40', NULL, 1);
INSERT INTO `sys_permission` VALUES (19, '列表', 'sys:role:list', NULL, '/sys/roles', NULL, 44, 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (20, '修改', 'sysGenerator:update', NULL, 'sysGenerator/update', NULL, 15, 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (21, '其他', NULL, 'layui-icon-list', NULL, NULL, 0, 200, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (22, '详情', 'sys:dept:detail', NULL, '/sys/dept/*', NULL, 32, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (23, '列表', 'sys:user:list', NULL, '/sys/users', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (24, '用户管理', NULL, NULL, '/index/users', '_self', 42, 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (25, '详情', 'sys:user:detail', NULL, '/sys/user/*', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (26, '删除', 'sys:permission:deleted', NULL, '/sys/permission/*', NULL, 11, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (27, '更新', 'sys:role:update', NULL, '/sys/role', NULL, 44, 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (28, '系统信息', '', '', '/index/systemInfo', '_self', 21, 1, 2, 1, '2020-05-26 14:21:47', '2020-05-26 14:23:01', 1);
INSERT INTO `sys_permission` VALUES (29, '更新', 'sys:dept:update', NULL, '/sys/dept', NULL, 32, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (30, '详情', 'sys:role:detail', NULL, '/sys/role/*', NULL, 44, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (31, '编辑', 'sys:permission:update', NULL, '/sys/permission', NULL, 11, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (32, '部门管理', NULL, NULL, '/index/depts', '_self', 42, 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (33, '新增', 'sys:user:add', NULL, '/sys/user', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (34, '列表', 'sys:permission:list', NULL, '/sys/permissions', NULL, 11, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (35, '新增', 'sys:permission:add', NULL, '/sys/permission', NULL, 11, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (36, '字典管理', NULL, '', '/index/sysDict', NULL, 45, 10, 2, 1, NULL, NULL, 1);
INSERT INTO `sys_permission` VALUES (37, '列表', 'sysDict:list', NULL, 'sysDict/listByPage', NULL, 36, 0, 3, 1, NULL, NULL, 1);
INSERT INTO `sys_permission` VALUES (38, '新增', 'sysDict:add', NULL, 'sysDict/add', NULL, 36, 0, 3, 1, NULL, NULL, 1);
INSERT INTO `sys_permission` VALUES (39, '修改', 'sysDict:update', NULL, 'sysDict/update', NULL, 36, 0, 3, 1, NULL, NULL, 1);
INSERT INTO `sys_permission` VALUES (40, '删除', 'sysDict:delete', NULL, 'sysDict/delete', NULL, 36, 0, 3, 1, NULL, NULL, 1);
INSERT INTO `sys_permission` VALUES (41, '表单构建', '', '', '/index/build', '_self', 21, 1, 2, 1, '2020-04-22 13:09:41', '2020-05-07 13:36:47', 1);
INSERT INTO `sys_permission` VALUES (42, '组织管理', NULL, 'layui-icon-user', NULL, NULL, 0, 1, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (43, '拥有角色', 'sys:user:role:detail', NULL, '/sys/user/roles/*', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (44, '角色管理', NULL, NULL, '/index/roles', '_self', 42, 99, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (45, '系统管理', NULL, 'layui-icon-set-fill', NULL, NULL, 0, 98, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (46, '定时任务暂停', 'sysJob:pause', NULL, '/sysJob/pause', '_self', 50, 1, 3, 1, '2020-04-22 15:48:18', NULL, 1);
INSERT INTO `sys_permission` VALUES (47, '更新', 'sys:user:update', NULL, '/sys/user', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (48, '删除', 'sys:user:deleted', NULL, '/sys/user', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (49, '删除', 'sys:log:deleted', NULL, '/sys/logs', NULL, 8, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES (50, '定时任务', NULL, NULL, '/index/sysJob', '_self', 45, 10, 2, 1, NULL, NULL, 1);
INSERT INTO `sys_permission` VALUES (51, '列表', 'sysJob:list', NULL, 'sysJob/listByPage', NULL, 50, 0, 3, 1, NULL, NULL, 1);
INSERT INTO `sys_permission` VALUES (52, '新增', 'sysJob:add', NULL, 'sysJob/add', NULL, 50, 0, 3, 1, NULL, NULL, 1);
INSERT INTO `sys_permission` VALUES (53, '修改', 'sysJob:update', NULL, 'sysJob/update', NULL, 50, 0, 3, 1, NULL, NULL, 1);
INSERT INTO `sys_permission` VALUES (54, '删除', 'sysJob:delete', NULL, 'sysJob/delete', NULL, 50, 0, 3, 1, NULL, NULL, 1);
INSERT INTO `sys_role_permission` VALUES (1, 1, 1, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (2, 1, 2, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (3, 1, 3, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (4, 1, 4, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (5, 1, 5, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (6, 1, 6, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (7, 1, 7, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (8, 1, 8, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (9, 1, 9, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (10, 1, 10, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (11, 1, 11, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (12, 1, 12, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (13, 1, 13, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (14, 1, 14, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (15, 1, 15, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (16, 1, 16, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (17, 1, 17, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (18, 1, 18, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (19, 1, 19, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (20, 1, 20, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (21, 1, 21, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (22, 1, 22, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (23, 1, 23, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (24, 1, 24, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (25, 1, 25, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (26, 1, 26, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (27, 1, 27, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (28, 1, 28, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (29, 1, 29, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (30, 1, 30, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (31, 1, 31, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (32, 1, 32, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (33, 1, 33, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (34, 1, 34, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (35, 1, 35, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (36, 1, 36, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (37, 1, 37, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (38, 1, 38, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (39, 1, 39, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (40, 1, 40, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (41, 1, 41, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (42, 1, 42, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (43, 1, 43, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (44, 1, 44, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (45, 1, 45, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (46, 1, 46, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (47, 1, 47, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (48, 1, 48, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (49, 1, 49, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (50, 1, 50, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (51, 1, 51, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (52, 1, 52, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (53, 1, 53, '2020-07-31 14:04:32');
INSERT INTO `sys_role_permission` VALUES (54, 1, 54, '2020-07-31 14:04:32');
INSERT INTO `sys_dict` VALUES (1, 'sex', '性别', '2020-04-30 17:24:09');
INSERT INTO `sys_dict_detail` VALUES (1, '男', '1', 1, 1, '2020-04-30 17:24:19');
INSERT INTO `sys_dict_detail` VALUES (2, '女', '2', 2, 1, '2020-04-30 17:24:25');
INSERT INTO `sys_job` VALUES (1, 'testTask', '1', '*/5 * * * * ?', 1, '1', '2020-04-22 16:58:35');
INSERT INTO `sys_job` VALUES (2, 'systemTask', '', '*/5 * * * * ?', 1, '服务器系统信息推送定时任务', '2020-08-27 17:05:49');
