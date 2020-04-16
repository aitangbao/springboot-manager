/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : company_project

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 20/03/2020 18:04:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `dept_no` varchar(18) DEFAULT NULL COMMENT '部门编号(规则：父级关系编码+自己的编码)',
  `name` varchar(300) DEFAULT NULL COMMENT '部门名称',
  `pid` varchar(64) NOT NULL COMMENT '父级id',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态(1:正常；0:弃用)',
  `relation_code` varchar(3000) DEFAULT NULL COMMENT '为了维护更深层级关系',
  `dept_manager_id` varchar(64) DEFAULT NULL COMMENT '部门经理user_id',
  `manager_name` varchar(255) DEFAULT NULL COMMENT '部门经理名称',
  `phone` varchar(20) DEFAULT NULL COMMENT '部门经理联系电话',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES ('4bd0b0a3-097d-4902-a1f7-641ea3b771bd', 'YXD000002', '部门1', '72a4f388-50f8-4019-8c67-530cd7c74e7a', 1, 'YXD000001YXD000002', NULL, '张杨', '13899999999', '2019-11-05 12:24:50', '2019-11-09 21:22:55', 1);
INSERT INTO `sys_dept` VALUES ('72a4f388-50f8-4019-8c67-530cd7c74e7a', 'YXD000001', '总公司', '0', 1, 'YXD000001', NULL, '小李', '13888888888', '2019-11-07 22:43:33', NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_generator
-- ----------------------------
DROP TABLE IF EXISTS `sys_generator`;
CREATE TABLE `sys_generator` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'ID',
  `table_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '表名',
  `menu_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单名称',
  `pid` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '上级菜单',
  `gen_time` datetime DEFAULT NULL  COMMENT '上次生成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin  COMMENT='代码生成';

-- ----------------------------
-- Records of sys_generator
-- ----------------------------
BEGIN;
INSERT INTO `sys_generator` VALUES ('1', 'sys_dept', '部门管理', '0', '2020-03-20 13:16:47');
INSERT INTO `sys_generator` VALUES ('2', 'sys_generator', '代码生成', '0', '2020-03-20 13:16:47');
INSERT INTO `sys_generator` VALUES ('3', 'sys_log', '日志管理', '0', '2020-03-20 13:16:47');
INSERT INTO `sys_generator` VALUES ('4', 'sys_permission', '权限管理', '0', '2020-03-20 13:16:47');
INSERT INTO `sys_generator` VALUES ('5', 'sys_role', '角色管理', '0', '2020-03-20 13:16:47');
INSERT INTO `sys_generator` VALUES ('6', 'sys_role_permission', '角色权限管理', '0', '2020-03-20 13:16:47');
INSERT INTO `sys_generator` VALUES ('7', 'sys_user', '用户管理', '0', '2020-03-20 13:16:47');
INSERT INTO `sys_generator` VALUES ('8', 'sys_user_role', '用户角色管理', '0', '2020-03-20 13:16:47');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `time` int(11) DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单权限名称',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问地址URL',
  `target` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'a target属性:_self _blank',
  `pid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级菜单权限名称',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '排序',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '菜单权限类型(1:目录;2:菜单;3:按钮)',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态1:正常 0：禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) NULL DEFAULT 1 COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统权限' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES ('010bcf8b-3172-4331-9941-25788ca8cbb0', '删除', 'sysGenerator:delete', 'sysGenerator/delete', NULL, '43d1bbf7-fc4a-4a10-9ad1-205b16c7c05f', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('0d99b687-3f46-4632-9d56-8dd5e476dae7', 'SQL 监控', NULL, '/druid/sql.html', '_blank', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 98, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('1a2ec857-e775-4377-9fb7-e3c77738b3e5', '新增', 'sys:role:add', '/sys/role', NULL, 'e0b16b95-09de-4d60-a283-1eebd424ed47', 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('1dec779d-a9ec-448a-9389-a2b4eefce119', '添加', 'sysGenerator:add', 'sysGenerator/add', NULL, '43d1bbf7-fc4a-4a10-9ad1-205b16c7c05f', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('2073345f-7344-43fe-9084-b7add56da652', '删除', 'sys:dept:deleted', '/sys/dept/*', NULL, 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('26764d88-1d90-402d-b355-a75deef116f2', '接口管理', NULL, '/doc.html', '_blank', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('355f387f-a22b-4f8c-9cd6-ae10e930cd70', '列表', 'sys:log:list', '/sys/logs', NULL, '37101ed5-e840-4082-ae33-682ca6e41ad8', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('37101ed5-e840-4082-ae33-682ca6e41ad8', '日志管理', NULL, '/index/logs', '_self', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 97, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('3a93a7e3-956a-408e-b2e4-108e9ece8f04', '新增', 'sys:dept:add', '/sys/dept', NULL, 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('3c390dfd-0d9a-46de-9a5b-1ed884febcb2', '赋予角色', 'sys:user:role:update', '/sys/user/roles/*', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('3dac936c-c4e1-4560-ac93-905502f61ae0', '菜单权限管理', NULL, '/index/menus', '_self', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 98, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('3ed79f23-90bf-4669-bc02-42ae392e75c1', '列表', 'sys:dept:list', '/sys/depts', NULL, 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('4018e179-e599-41d0-bac5-c5408e1d4bc6', '删除', 'sys:role:deleted', '/sys/role/*', NULL, 'e0b16b95-09de-4d60-a283-1eebd424ed47', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('43d1bbf7-fc4a-4a10-9ad1-205b16c7c05f', '代码生成', NULL, '/index/sysGenerator', '_self', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 1, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('46dbb867-51d6-4523-852f-b12e75b34e3d', '列表', 'sysGenerator:list', 'sysGenerator/listByPage', NULL, '43d1bbf7-fc4a-4a10-9ad1-205b16c7c05f', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('475b4c24-40fa-4823-863a-ba6d793b7610', '详情', 'sys:permission:detail', '/sys/permission/*', NULL, '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('58612968-d93c-4c21-8fdc-a825c0ab0275', '列表', 'sys:role:list', '/sys/roles', NULL, 'e0b16b95-09de-4d60-a283-1eebd424ed47', 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('60c3443a-5ec9-4ea7-9484-d2870af93059', '修改', 'sysGenerator:update', 'sysGenerator/update', NULL, '43d1bbf7-fc4a-4a10-9ad1-205b16c7c05f', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('65bdb02f47b94e71c2a2b9e459bde909', '其他', NULL, NULL, NULL, '0', 200, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('761db494-833d-4a6c-94b4-3a7409fd9a78', '详情', 'sys:dept:detail', '/sys/dept/*', NULL, 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('783aedd8-5d93-46b6-8c6d-e4d3f0f3f466', '列表', 'sys:user:list', '/sys/users', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('78f8e29a-cccd-49e5-ada7-5af40dd95312', '用户管理', NULL, '/index/users', '_self', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('817a58d1-ec82-4106-870a-bcc0bfaee0e7', '详情', 'sys:user:detail', '/sys/user/*', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('8623c941-5746-4667-9fb8-76f6f5059788', '删除', 'sys:permission:deleted', '/sys/permission/*', NULL, '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('992d1a8d-b5f8-44fc-9a48-4b3e60a7b15e', '更新', 'sys:role:update', '/sys/role', NULL, 'e0b16b95-09de-4d60-a283-1eebd424ed47', 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('a390845b-a53d-4bc9-af5d-331c37f34e6f', '更新', 'sys:dept:update', '/sys/dept', NULL, 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('b01614ab-0538-4cca-bb61-b46f18c60aa4', '详情', 'sys:role:detail', '/sys/role/*', NULL, 'e0b16b95-09de-4d60-a283-1eebd424ed47', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('b180aafa-0d1a-4898-b838-bc20cd44356d', '编辑', 'sys:permission:update', '/sys/permission', NULL, '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('c038dc93-f30d-4802-a090-be352eab341a', '部门管理', NULL, '/index/depts', '_self', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('c0a84726-47d8-4d7a-8d53-0736a4586647', '新增', 'sys:user:add', '/sys/user', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('c30389e8-eb3e-4a0d-99c4-639e1893a05f', '列表', 'sys:permission:list', '/sys/permissions', NULL, '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('c30389e8-eb3e-4a0d-99c4-639e1893f50a', '新增', 'sys:permission:add', '/sys/permission', NULL, '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('d6214dcb-8b6d-494b-88fa-f519fc08ff8f', '组织管理', NULL, NULL, NULL, '0', 1, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('db2d31b7-fdcb-478e-bfde-a55eb8b0aa43', '拥有角色', 'sys:user:role:detail', '/sys/user/roles/*', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('e0b16b95-09de-4d60-a283-1eebd424ed47', '角色管理', NULL, '/index/roles', '_self', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 99, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', '系统管理', NULL, NULL, NULL, '0', 98, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('f21ed5e8-0756-45dc-91c5-f58a9463caaa', '更新', 'sys:user:update', '/sys/user', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('f28b9215-3119-482d-bdc1-1f4c3f7c0869', '删除', 'sys:user:deleted', '/sys/user', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO `sys_permission` VALUES ('f2ff9320-c643-4c85-8b68-15f86d47b88b', '删除', 'sys:log:deleted', '/sys/logs', NULL, '37101ed5-e840-4082-ae33-682ca6e41ad8', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(300) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1' COMMENT '状态(1:正常0:弃用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '超级管理员', '拥有所有权限-不能删除', 1, '2019-11-01 19:26:29', '2020-03-19 13:29:51', 1);
INSERT INTO `sys_role` VALUES ('2d56198c-d14b-4d02-a625-7559815b62fb', '普通用户角色', '只读', 1, '2019-11-09 22:49:18', '2020-01-01 19:59:46', 1);
INSERT INTO `sys_role` VALUES ('8dd881c7-078c-406a-9c5f-242ab9ecfcb5', 'test', '测试', 1, '2020-01-01 20:01:58', '2019-11-19 10:43:05', 1);
INSERT INTO `sys_role` VALUES ('b95c69b7-84be-430f-ae57-27a703ae3998', '后台管理员', '一般是程序员拥有，用来初始化菜单权限', 1, '2019-11-09 21:25:31', NULL, 1);
INSERT INTO `sys_role` VALUES ('de54c167-e733-4b5b-83dd-ce10edd078f5', '超级权限', '', 1, '2019-11-19 10:34:48', '2019-11-19 10:49:39', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色id',
  `permission_id` varchar(64) DEFAULT NULL COMMENT '菜单权限id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色权限';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES ('0416009f-d518-4b60-a6c9-095f5c1986f7', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2020-01-01 20:01:58');
INSERT INTO `sys_role_permission` VALUES ('04446925-82bb-4ea1-b91d-a5a191ba04de', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'c30389e8-eb3e-4a0d-99c4-639e1893f50a', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('045ee52f-06e1-4c07-9e2e-25593aefd03a', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', '3ed79f23-90bf-4669-bc02-42ae392e75c1', '2020-01-01 20:01:58');
INSERT INTO `sys_role_permission` VALUES ('0469d515-469a-45dc-8809-e1ee9c5f3959', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('04df7dba-cd4a-44ce-a697-443846dd6a08', '2d56198c-d14b-4d02-a625-7559815b62fb', '783aedd8-5d93-46b6-8c6d-e4d3f0f3f466', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('05538029-4906-405b-906a-3fe7ad65b647', '2d56198c-d14b-4d02-a625-7559815b62fb', 'c038dc93-f30d-4802-a090-be352eab341a', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('0e19259339320a21c2703fdef8ddc06a', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '26764d88-1d90-402d-b355-a75deef116f2', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('14935f00-9352-433e-8f2f-8607489d8932', '2d56198c-d14b-4d02-a625-7559815b62fb', '58612968-d93c-4c21-8fdc-a825c0ab0275', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('163625ac55ead52a28303fafc15ea754', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '37101ed5-e840-4082-ae33-682ca6e41ad8', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('16f52080-3fd5-426c-8735-72d44ee401ce', 'b95c69b7-84be-430f-ae57-27a703ae3998', '4018e179-e599-41d0-bac5-c5408e1d4bc6', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('170620ab-ce2a-4b66-b7e3-b491088cc05d', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2020-01-01 20:01:58');
INSERT INTO `sys_role_permission` VALUES ('1712fa00-97fd-4aec-b61b-df6a946d352d', 'b95c69b7-84be-430f-ae57-27a703ae3998', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('1cf056c9-6447-49d2-bd48-2ef64a180f18', '2d56198c-d14b-4d02-a625-7559815b62fb', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('2171647c73b1379f0b989834c9a952c3', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('27323bb932f2db757366c9017be68dfc', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '783aedd8-5d93-46b6-8c6d-e4d3f0f3f466', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('2846bc18-1efd-46b0-80c3-cf52e773828e', 'b95c69b7-84be-430f-ae57-27a703ae3998', '0d99b687-3f46-4632-9d56-8dd5e476dae7', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('297811ff56fb9ebc19815ac9f08647ae', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '43d1bbf7-fc4a-4a10-9ad1-205b16c7c05f', NULL);
INSERT INTO `sys_role_permission` VALUES ('2d7dc7ba-7e79-4f60-b7e0-0aaa979bd7ee', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'b01614ab-0538-4cca-bb61-b46f18c60aa4', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('2e645fba-1bd5-48d4-bd20-c2cb7983e26f', '2d56198c-d14b-4d02-a625-7559815b62fb', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('2e9b349e-f6a5-499c-937d-a945478accab', 'b95c69b7-84be-430f-ae57-27a703ae3998', '26764d88-1d90-402d-b355-a75deef116f2', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('31bd294f-124e-431f-ac81-da8f689c5e9e', '2d56198c-d14b-4d02-a625-7559815b62fb', '355f387f-a22b-4f8c-9cd6-ae10e930cd70', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('3537366c-49ae-4394-af52-7334537a8105', 'de54c167-e733-4b5b-83dd-ce10edd078f5', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2019-11-19 10:49:39');
INSERT INTO `sys_role_permission` VALUES ('354bc4eb5a0240ad8597f1fc6e406d85', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f28b9215-3119-482d-bdc1-1f4c3f7c0869', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('373b5fe0-01db-46cb-acc3-382e525ec42f', '2d56198c-d14b-4d02-a625-7559815b62fb', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('37c85e7eab3fc9b1ee953d11686e3083', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '60c3443a-5ec9-4ea7-9484-d2870af93059', NULL);
INSERT INTO `sys_role_permission` VALUES ('44199ddc4ddf280f3fc04a478f962296', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '4018e179-e599-41d0-bac5-c5408e1d4bc6', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('45afb550-8ce5-46eb-bc8d-310023725ac8', 'b95c69b7-84be-430f-ae57-27a703ae3998', '992d1a8d-b5f8-44fc-9a48-4b3e60a7b15e', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('46745b75-b3f8-4b61-9fc9-535cb5cb2a15', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('55cf5c38-a9ff-4685-b12f-8c558781c648', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'b180aafa-0d1a-4898-b838-bc20cd44356d', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('5a4d026018b0f4040ab3b2f9a53b11f9', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('5fa50c70-1741-4aa5-85e4-c61fde3463bc', '2d56198c-d14b-4d02-a625-7559815b62fb', '3ed79f23-90bf-4669-bc02-42ae392e75c1', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('615bce0b5362397246a926e79ece627b', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c30389e8-eb3e-4a0d-99c4-639e1893f50a', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('6e11b7e811a053730c0da613d8fb71a1', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '65bdb02f47b94e71c2a2b9e459bde909', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('74c5067acfce2e5dca98489bf133c5d6', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c0a84726-47d8-4d7a-8d53-0736a4586647', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('77bb2db3e746921fabfddb3a335ac853', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'a390845b-a53d-4bc9-af5d-331c37f34e6f', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('77c577cd8da4a245b987fd748609ece5', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '355f387f-a22b-4f8c-9cd6-ae10e930cd70', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('787aa32c3e5dfe5e621a3ae33f42e146', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '010bcf8b-3172-4331-9941-25788ca8cbb0', NULL);
INSERT INTO `sys_role_permission` VALUES ('80f9eed3fde1f3dfc789a2d29cd00dd2', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f21ed5e8-0756-45dc-91c5-f58a9463caaa', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('84b925dcbd154e292bf4c353c1c20c66', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c038dc93-f30d-4802-a090-be352eab341a', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('84c8ea44-854b-4497-96c0-d2593bfa446e', 'b95c69b7-84be-430f-ae57-27a703ae3998', '1a2ec857-e775-4377-9fb7-e3c77738b3e5', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('87355ec0-6e70-464a-ae43-302adc9cd875', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2020-01-01 20:01:58');
INSERT INTO `sys_role_permission` VALUES ('890428f5-9910-408a-8173-d46370c4236d', '2d56198c-d14b-4d02-a625-7559815b62fb', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('8bceed2721608397abfd03b5bf7b7f87', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('8c51ef1f65cba07ec635777072053ccc', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '78f8e29a-cccd-49e5-ada7-5af40dd95312', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('8d679dba4cec5df3cadcb0ab7b190814', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'b180aafa-0d1a-4898-b838-bc20cd44356d', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('8dde0149-ba66-46ed-9145-20efa5a33f96', '2d56198c-d14b-4d02-a625-7559815b62fb', '37101ed5-e840-4082-ae33-682ca6e41ad8', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('8fb1212eedc88cfa2b59c03beb8a0dbe', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '1a2ec857-e775-4377-9fb7-e3c77738b3e5', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('9095428be970678844fd7c2a7e2af31b', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '1dec779d-a9ec-448a-9389-a2b4eefce119', NULL);
INSERT INTO `sys_role_permission` VALUES ('9b65792e-6e14-4dab-b0a4-58df7d4b1a55', '2d56198c-d14b-4d02-a625-7559815b62fb', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('aac670c36557751fdda7c316b29e861b', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '58612968-d93c-4c21-8fdc-a825c0ab0275', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('ad7e8be78121fa8b643a91c3c2488674', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '992d1a8d-b5f8-44fc-9a48-4b3e60a7b15e', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('b02553793d62afea66c9aab5a56b01d3', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3a93a7e3-956a-408e-b2e4-108e9ece8f04', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('b0d755ca2e3486a3e72f6fa57594e4c7', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'db2d31b7-fdcb-478e-bfde-a55eb8b0aa43', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('b2379db3-09cd-41f7-acff-4dc7724036d8', 'b95c69b7-84be-430f-ae57-27a703ae3998', '355f387f-a22b-4f8c-9cd6-ae10e930cd70', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('b2ba9c26-03a6-41df-a6b5-bc52b5045d53', 'de54c167-e733-4b5b-83dd-ce10edd078f5', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2019-11-19 10:49:39');
INSERT INTO `sys_role_permission` VALUES ('b353be32-5661-4297-be53-f55fb8973eaa', 'b95c69b7-84be-430f-ae57-27a703ae3998', '475b4c24-40fa-4823-863a-ba6d793b7610', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('bac17f9a849cb90114e6c12f0f8a7212', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '475b4c24-40fa-4823-863a-ba6d793b7610', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('bf9acb71c1b1af37432905c94acdb33f', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3ed79f23-90bf-4669-bc02-42ae392e75c1', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('c051a2462a2f36b6ae0e98d762eb96e5', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2073345f-7344-43fe-9084-b7add56da652', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('c3d2afff-d21b-40ae-ac52-d563879fcf24', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', 'c038dc93-f30d-4802-a090-be352eab341a', '2020-01-01 20:01:58');
INSERT INTO `sys_role_permission` VALUES ('c472139f3afc35c0689956326f47cdb3', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'b01614ab-0538-4cca-bb61-b46f18c60aa4', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('c586d46f6e8ca13e1184446b46603343', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f2ff9320-c643-4c85-8b68-15f86d47b88b', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('c7e337ed17fcfc6ca9746d42d7327934', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '0d99b687-3f46-4632-9d56-8dd5e476dae7', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('ca9a7374489135c8fee811a2992cdab1', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '46dbb867-51d6-4523-852f-b12e75b34e3d', NULL);
INSERT INTO `sys_role_permission` VALUES ('cbfb8eb5-d44e-47d7-880a-45b976e93184', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('d3928837-f0cb-407f-bf97-eb11019b31ae', '2d56198c-d14b-4d02-a625-7559815b62fb', '78f8e29a-cccd-49e5-ada7-5af40dd95312', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('d3e419f0-6c81-4a58-a86e-48b61e003109', 'b95c69b7-84be-430f-ae57-27a703ae3998', '37101ed5-e840-4082-ae33-682ca6e41ad8', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('d6ddd178-0e8d-43c6-8916-abf363ffa39f', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'f2ff9320-c643-4c85-8b68-15f86d47b88b', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('dbe2e7ec17a51ce53aea672de503bba0', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3c390dfd-0d9a-46de-9a5b-1ed884febcb2', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('dd951c39-4493-492b-b77a-93247629f4f2', 'b95c69b7-84be-430f-ae57-27a703ae3998', '8623c941-5746-4667-9fb8-76f6f5059788', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('e35ecb61-5474-4e9c-afc9-0fd8ad26fb5d', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('e460180b8b661f826f449c19c390309a', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '817a58d1-ec82-4106-870a-bcc0bfaee0e7', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('ec661250e52b678e08ce1a2794418fd3', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('edad371b-820f-42e9-b88f-46dc1476edc6', 'b95c69b7-84be-430f-ae57-27a703ae3998', '58612968-d93c-4c21-8fdc-a825c0ab0275', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('f58d9e73-8929-4c90-a466-afc446339f97', 'de54c167-e733-4b5b-83dd-ce10edd078f5', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2019-11-19 10:49:39');
INSERT INTO `sys_role_permission` VALUES ('f7c8a6223c1f1e0b819a471c8cc4bd03', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('f931ea71da5162f62bcd1209cf858be5', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '761db494-833d-4a6c-94b4-3a7409fd9a78', '2020-03-19 13:29:51');
INSERT INTO `sys_role_permission` VALUES ('fac580ccf86088fd6757586eea8a5a84', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '8623c941-5746-4667-9fb8-76f6f5059788', '2020-03-19 13:29:51');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '账户名称',
  `salt` varchar(20) DEFAULT NULL COMMENT '加密盐值',
  `password` varchar(200) NOT NULL COMMENT '用户密码密文',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `dept_id` varchar(64) DEFAULT NULL COMMENT '部门id',
  `real_name` varchar(60) DEFAULT NULL COMMENT '真实名称',
  `nick_name` varchar(60) DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱(唯一)',
  `status` tinyint(4) DEFAULT '1' COMMENT '账户状态(1.正常 2.锁定 )',
  `sex` tinyint(4) DEFAULT '1' COMMENT '性别(1.男 2.女)',
  `deleted` tinyint(4) DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  `create_id` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_id` varchar(64) DEFAULT NULL COMMENT '更新人',
  `create_where` tinyint(4) DEFAULT '1' COMMENT '创建来源(1.web 2.android 3.ios )',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1dfaafa7-fddf-46f2-b3d8-11bfe9ac7230', 'dev123', 'fa386978e2c04b7baef1', '9204993352a150ddd9febc421a5e5636', '131777777777', '4bd0b0a3-097d-4902-a1f7-641ea3b771bd', NULL, NULL, NULL, 1, 1, 1, NULL, 'fcf34b56-a7a2-4719-9236-867495e74c31', 1, '2019-11-09 22:47:30', '2020-03-19 02:39:12');
INSERT INTO `sys_user` VALUES ('7f8c0e32-058e-409d-8e7c-22a9afe6a0a0', 'zhangyang', '062f33e33afe4509b24b', '920f5d75077b25912e5054c4d58e0a4c', '13899999999', '4bd0b0a3-097d-4902-a1f7-641ea3b771bd', '张杨', NULL, '16399@163.com', 1, 1, 1, NULL, '7f8c0e32-058e-409d-8e7c-22a9afe6a0a0', 1, '2019-11-09 21:23:36', '2019-11-09 22:45:36');
INSERT INTO `sys_user` VALUES ('cd58111d005641a162cb2528c71889ae', 'admin123123', 'db7b27ee460d47a0ad1f', 'f9ac8bba2c66976c73120fcd41a5a79a', '123123123', '4bd0b0a3-097d-4902-a1f7-641ea3b771bd', NULL, NULL, NULL, 1, 1, 1, NULL, NULL, 1, '2020-03-18 09:49:31', NULL);
INSERT INTO `sys_user` VALUES ('d860412c-9a4b-404b-8b71-ae8e3f4c27b7', 't', 'a8aed440045b4e0c9c69', 'b08f907d879ea98a681df0082a9cb95e', '13899999999', '72a4f388-50f8-4019-8c67-530cd7c74e7a', NULL, NULL, NULL, 1, 1, 1, NULL, NULL, 1, '2019-11-19 10:34:24', NULL);
INSERT INTO `sys_user` VALUES ('fcf34b56-a7a2-4719-9236-867495e74c31', 'admin', '324ce32d86224b00a02b', '2102b59a75ab87616b62d0b9432569d0', '13888888888', '72a4f388-50f8-4019-8c67-530cd7c74e7a', '爱糖宝12', '小霍', 'yingxue@163.com', 1, 2, 1, NULL, 'fcf34b56-a7a2-4719-9236-867495e74c31', 3, '2019-09-22 19:38:05', '2020-03-18 09:15:22');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(64) NOT NULL COMMENT '用户id',
  `user_id` varchar(64) DEFAULT NULL,
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('2ebca69cc810541c81348d72aa5cab98', '7884efa332130c80be43ef1c61d91f8b', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', '2020-03-19 02:23:13');
INSERT INTO `sys_user_role` VALUES ('33137e13-2318-42e4-a8e7-4bb7eea0f4ef', '1ff14b2c-d32b-496b-8fb7-d988b838b8e0', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2020-03-18 08:16:12');
INSERT INTO `sys_user_role` VALUES ('69d5bf3a-37a8-4da1-8941-fe44caa51af0', '7f8c0e32-058e-409d-8e7c-22a9afe6a0a0', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2019-11-09 21:25:49');
INSERT INTO `sys_user_role` VALUES ('d797bb615b355118d35929ffa573cc86', 'ffd81881f21b48529086233f832f3480', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', '2020-03-19 02:23:02');
INSERT INTO `sys_user_role` VALUES ('da3dd29ea2f7957c144149e5040ce275', 'fcf34b56-a7a2-4719-9236-867495e74c31', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2020-03-19 12:17:45');
INSERT INTO `sys_user_role` VALUES ('ea228f70b42849dfffeffdcad04c3a45', 'ee477bad852b0177b3fa10a2243c98d2', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', '2020-03-19 02:34:47');
INSERT INTO `sys_user_role` VALUES ('f8ad372b79490bd1ad99e7ac77409647', '1dfaafa7-fddf-46f2-b3d8-11bfe9ac7230', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2020-03-19 02:39:01');
INSERT INTO `sys_user_role` VALUES ('fd1512dc-bf59-45b2-aa14-93da48ff0e8c', 'd860412c-9a4b-404b-8b71-ae8e3f4c27b7', 'de54c167-e733-4b5b-83dd-ce10edd078f5', '2019-11-19 10:35:14');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
