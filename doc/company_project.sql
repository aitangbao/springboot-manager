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

 Date: 18/03/2020 17:21:59
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES ('4bd0b0a3-097d-4902-a1f7-641ea3b771bd', 'YXD000004', '部门1', '72a4f388-50f8-4019-8c67-530cd7c74e7a', 1, 'YXD000004', NULL, '张杨', '13899999999', '2019-11-05 12:24:50', '2019-11-09 21:22:55', 1);
INSERT INTO `sys_dept` VALUES ('72a4f388-50f8-4019-8c67-530cd7c74e7a', 'YXD000005', '总公司', '0', 1, 'YXD000005YXD000004', NULL, '小李', '13888888888', '2019-11-07 22:43:33', NULL, 1);
INSERT INTO `sys_dept` VALUES ('a4f3e984-622b-4330-bcda-0ea01e44d299', 'YXD000005', '部门2', '72a4f388-50f8-4019-8c67-530cd7c74e7a', 1, 'YXD000005YXD000004YXD000005', NULL, '小张', '13899999999', '2020-01-01 19:58:17', NULL, 1);
INSERT INTO `sys_dept` VALUES ('a7461c9b-6668-47c6-b0bf-5197abbd2337', 'YXD000001', 't', '0', 1, 'YXD000001', NULL, '123', '123', '2020-03-18 08:58:11', NULL, 1);
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
CREATE TABLE `sys_permission` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `code` varchar(64) DEFAULT NULL COMMENT '菜单权限编码',
  `name` varchar(300) DEFAULT NULL COMMENT '菜单权限名称',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `url` varchar(100) DEFAULT NULL COMMENT '访问地址URL',
  `method` varchar(10) DEFAULT NULL COMMENT '资源请求类型',
  `pid` varchar(64) DEFAULT NULL COMMENT '父级菜单权限名称',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `type` tinyint(4) DEFAULT NULL COMMENT '菜单权限类型(1:目录;2:菜单;3:按钮)',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态1:正常 0：禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES ('0d99b687-3f46-4632-9d56-8dd5e476dae7', '', 'SQL 监控', '', '/druid/sql.html', 'GET', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 98, 2, 1, '2019-11-09 20:58:23', '2019-11-09 20:59:57', 1);
INSERT INTO `sys_permission` VALUES ('1a2ec857-e775-4377-9fb7-e3c77738b3e5', 'btn-role-add', '新增', 'sys:role:add', '/sys/role', 'POST', 'e0b16b95-09de-4d60-a283-1eebd424ed47', 0, 3, 1, '2019-09-22 16:00:59', NULL, 1);
INSERT INTO `sys_permission` VALUES ('2073345f-7344-43fe-9084-b7add56da652', 'btn-dept-deleted', '删除', 'sys:dept:deleted', '/sys/dept/*', 'DELETED', 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2019-11-09 20:49:59', NULL, 1);
INSERT INTO `sys_permission` VALUES ('26764d88-1d90-402d-b355-a75deef116f2', '', '接口管理', '', '/doc.html', 'GET', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 100, 2, 1, '2019-11-09 20:56:37', '2020-03-18 03:22:52', 1);
INSERT INTO `sys_permission` VALUES ('26e66825-5ca9-4470-b7dc-9e710b2563ef', 'btn-user-list', '列表', 'sys:user:list', '/sys/users', 'POST', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 89, 3, 1, '2020-01-01 19:31:56', '2020-01-01 19:43:33', 0);
INSERT INTO `sys_permission` VALUES ('355f387f-a22b-4f8c-9cd6-ae10e930cd70', 'btn-logs-list', '列表', 'sys:log:list', '/sys/logs', 'POST', '37101ed5-e840-4082-ae33-682ca6e41ad8', 100, 3, 1, '2019-11-09 21:00:49', '2019-11-09 21:02:08', 1);
INSERT INTO `sys_permission` VALUES ('37101ed5-e840-4082-ae33-682ca6e41ad8', '', '日志管理', '', '/index/logs', 'GET', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 100, 2, 1, '2019-11-09 20:59:09', NULL, 1);
INSERT INTO `sys_permission` VALUES ('3a93a7e3-956a-408e-b2e4-108e9ece8f04', 'btn-dept-add', '新增', 'sys:dept:add', '/sys/dept', 'POST', 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2019-11-07 22:42:49', '2019-11-09 20:51:08', 1);
INSERT INTO `sys_permission` VALUES ('3c390dfd-0d9a-46de-9a5b-1ed884febcb2', 'btn-user-role-update', '赋予角色', 'sys:user:role:update', '/sys/user/roles/*', 'POST', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2019-11-09 20:39:09', NULL, 1);
INSERT INTO `sys_permission` VALUES ('3dac936c-c4e1-4560-ac93-905502f61ae0', NULL, '菜单权限管理', '', '/index/menus', 'GET', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 98, 2, 1, '2019-09-22 15:18:12', '2019-11-09 20:59:33', 1);
INSERT INTO `sys_permission` VALUES ('3ed79f23-90bf-4669-bc02-42ae392e75c1', 'btn-dept-list', '列表', 'sys:dept:list', '/sys/depts', 'POST', 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2019-11-07 22:38:34', '2019-11-09 20:51:18', 1);
INSERT INTO `sys_permission` VALUES ('4018e179-e599-41d0-bac5-c5408e1d4bc6', 'btn-role-deleted', '删除', 'sys:role:deleted', '/sys/role/*', 'DELETED', 'e0b16b95-09de-4d60-a283-1eebd424ed47', 100, 3, 1, '2019-11-09 20:54:28', NULL, 1);
INSERT INTO `sys_permission` VALUES ('475b4c24-40fa-4823-863a-ba6d793b7610', 'btn-permission-detail', '详情', 'sys:permission:detail', '/sys/permission/*', 'GET', '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2019-11-09 20:43:05', '2020-03-18 08:53:38', 1);
INSERT INTO `sys_permission` VALUES ('58612968-d93c-4c21-8fdc-a825c0ab0275', 'btn-role-list', '列表', 'sys:role:list', '/sys/roles', 'POST', 'e0b16b95-09de-4d60-a283-1eebd424ed47', 0, 3, 1, '2019-09-22 16:04:33', NULL, 1);
INSERT INTO `sys_permission` VALUES ('761db494-833d-4a6c-94b4-3a7409fd9a78', 'btn-dept-detail', '详情', 'sys:dept:detail', '/sys/dept/*', 'GET', 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2019-11-09 20:50:53', NULL, 1);
INSERT INTO `sys_permission` VALUES ('783aedd8-5d93-46b6-8c6d-e4d3f0f3f466', 'btn-user-list', '列表', 'sys:user:list', '/sys/users', 'POST', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-01-01 19:44:37', NULL, 1);
INSERT INTO `sys_permission` VALUES ('78f8e29a-cccd-49e5-ada7-5af40dd95312', '', '用户管理', '', '/index/users', 'GET', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 100, 2, 1, '2020-01-01 19:30:30', '2019-11-09 20:48:29', 1);
INSERT INTO `sys_permission` VALUES ('817a58d1-ec82-4106-870a-bcc0bfaee0e7', 'btn-user-detail', '详情', 'sys:user:detail', '/sys/user/*', 'GET', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2019-11-09 20:24:24', '2019-11-09 20:48:05', 1);
INSERT INTO `sys_permission` VALUES ('8623c941-5746-4667-9fb8-76f6f5059788', 'btn-permission-deleted', '删除', 'sys:permission:deleted', '/sys/permission/*', 'DELETED', '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2019-11-07 22:35:50', '2019-11-09 20:44:44', 1);
INSERT INTO `sys_permission` VALUES ('992d1a8d-b5f8-44fc-9a48-4b3e60a7b15e', 'btn-role-update', '更新', 'sys:role:update', '/sys/role', 'PUT', 'e0b16b95-09de-4d60-a283-1eebd424ed47', 0, 3, 1, '2019-09-22 16:03:46', NULL, 1);
INSERT INTO `sys_permission` VALUES ('a390845b-a53d-4bc9-af5d-331c37f34e6f', 'btn-dept-update', '更新', 'sys:dept:update', '/sys/dept', 'PUT', 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2019-11-09 20:53:16', NULL, 1);
INSERT INTO `sys_permission` VALUES ('b01614ab-0538-4cca-bb61-b46f18c60aa4', 'btn-role-detail', '详情', 'sys:role:detail', '/sys/role/*', 'GET', 'e0b16b95-09de-4d60-a283-1eebd424ed47', 100, 3, 1, '2019-09-22 16:06:13', '2019-11-09 20:55:08', 1);
INSERT INTO `sys_permission` VALUES ('b180aafa-0d1a-4898-b838-bc20cd44356d', NULL, '编辑', 'sys:permission:update', '/sys/permission', 'PUT', '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2019-11-07 22:27:22', '2019-11-09 20:48:44', 1);
INSERT INTO `sys_permission` VALUES ('c038dc93-f30d-4802-a090-be352eab341a', '', '部门管理', '', '/index/depts', 'GET', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 100, 2, 1, '2019-11-07 22:37:20', '2019-11-09 20:48:22', 1);
INSERT INTO `sys_permission` VALUES ('c0a84726-47d8-4d7a-8d53-0736a4586647', 'btn-user-add', '新增', 'sys:user:add', '/sys/user', 'POST', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2019-11-09 20:25:18', NULL, 1);
INSERT INTO `sys_permission` VALUES ('c30389e8-eb3e-4a0d-99c4-639e1893a05f', 'btn-permission-list', '列表', 'sys:permission:list', '/sys/permissions', 'POST', '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2019-09-22 15:26:45', '2019-11-09 20:45:19', 1);
INSERT INTO `sys_permission` VALUES ('c30389e8-eb3e-4a0d-99c4-639e1893f50a', 'btn-permission-list', '新增', 'sys:permission:add', '/sys/permission', 'POST', '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2019-09-22 15:26:45', '2019-11-09 20:45:25', 1);
INSERT INTO `sys_permission` VALUES ('d6214dcb-8b6d-494b-88fa-f519fc08ff8f', NULL, '组织管理', '', 'xx.html', '', '0', 100, 1, 1, '2019-09-28 15:16:14', NULL, 1);
INSERT INTO `sys_permission` VALUES ('db2d31b7-fdcb-478e-bfde-a55eb8b0aa43', 'btn-user-role-detail', '拥有角色', 'sys:user:role:detail', '/sys/user/roles/*', 'GET', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2019-11-09 20:29:47', NULL, 1);
INSERT INTO `sys_permission` VALUES ('e0b16b95-09de-4d60-a283-1eebd424ed47', '', '角色管理', '', '/index/roles', 'GET', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 99, 2, 1, '2019-09-22 15:45:45', '2019-11-09 20:59:22', 1);
INSERT INTO `sys_permission` VALUES ('e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', '', '系统管理', '', 'xx.html', '', '0', 98, 1, 1, '2019-11-09 20:56:01', NULL, 1);
INSERT INTO `sys_permission` VALUES ('f21ed5e8-0756-45dc-91c5-f58a9463caaa', 'btn-user-update', '更新', 'sys:user:update', '/sys/user', 'PUT', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2019-11-09 20:23:20', NULL, 1);
INSERT INTO `sys_permission` VALUES ('f28b9215-3119-482d-bdc1-1f4c3f7c0869', 'btn-user-deleted', '删除', 'sys:user:deleted', '/sys/user', 'DELETED', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2019-11-09 20:26:45', NULL, 1);
INSERT INTO `sys_permission` VALUES ('f2ff9320-c643-4c85-8b68-15f86d47b88b', 'btn-log-deleted', '删除', 'sys:log:deleted', '/sys/logs', 'DELETED', '37101ed5-e840-4082-ae33-682ca6e41ad8', 100, 3, 1, '2019-11-09 21:01:49', NULL, 1);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '超级管理员', '拥有所有权限-不能删除', 1, '2019-11-01 19:26:29', '2019-11-09 22:46:24', 1);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
INSERT INTO `sys_role_permission` VALUES ('1016d4d3-13d8-460c-b7a2-260ac401359c', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '761db494-833d-4a6c-94b4-3a7409fd9a78', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('1159be66-0ff0-4357-8a43-2b74900d3bf5', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '58612968-d93c-4c21-8fdc-a825c0ab0275', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('122ffe35-5442-4a2b-b702-ed99d310bd81', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '26764d88-1d90-402d-b355-a75deef116f2', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('14935f00-9352-433e-8f2f-8607489d8932', '2d56198c-d14b-4d02-a625-7559815b62fb', '58612968-d93c-4c21-8fdc-a825c0ab0275', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('15d717f8-4190-4547-8089-5593780cec71', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c0a84726-47d8-4d7a-8d53-0736a4586647', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('16f52080-3fd5-426c-8735-72d44ee401ce', 'b95c69b7-84be-430f-ae57-27a703ae3998', '4018e179-e599-41d0-bac5-c5408e1d4bc6', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('170620ab-ce2a-4b66-b7e3-b491088cc05d', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2020-01-01 20:01:58');
INSERT INTO `sys_role_permission` VALUES ('1712fa00-97fd-4aec-b61b-df6a946d352d', 'b95c69b7-84be-430f-ae57-27a703ae3998', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('1cf056c9-6447-49d2-bd48-2ef64a180f18', '2d56198c-d14b-4d02-a625-7559815b62fb', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('1e0f03df-6fa0-408d-bafb-5654f80b6e38', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '1a2ec857-e775-4377-9fb7-e3c77738b3e5', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('2846bc18-1efd-46b0-80c3-cf52e773828e', 'b95c69b7-84be-430f-ae57-27a703ae3998', '0d99b687-3f46-4632-9d56-8dd5e476dae7', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('2a662021-2a0f-46aa-a821-cd3670d39b55', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '37101ed5-e840-4082-ae33-682ca6e41ad8', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('2d7dc7ba-7e79-4f60-b7e0-0aaa979bd7ee', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'b01614ab-0538-4cca-bb61-b46f18c60aa4', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('2e645fba-1bd5-48d4-bd20-c2cb7983e26f', '2d56198c-d14b-4d02-a625-7559815b62fb', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('2e9b349e-f6a5-499c-937d-a945478accab', 'b95c69b7-84be-430f-ae57-27a703ae3998', '26764d88-1d90-402d-b355-a75deef116f2', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('2eaaf0d0-ea5c-46a7-af08-5a32cb2b8aa2', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('31bd294f-124e-431f-ac81-da8f689c5e9e', '2d56198c-d14b-4d02-a625-7559815b62fb', '355f387f-a22b-4f8c-9cd6-ae10e930cd70', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('3537366c-49ae-4394-af52-7334537a8105', 'de54c167-e733-4b5b-83dd-ce10edd078f5', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2019-11-19 10:49:39');
INSERT INTO `sys_role_permission` VALUES ('373b5fe0-01db-46cb-acc3-382e525ec42f', '2d56198c-d14b-4d02-a625-7559815b62fb', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('4056be9d-ed3b-446e-aba1-882f864b8da3', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3a93a7e3-956a-408e-b2e4-108e9ece8f04', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('4068f21e-6dbc-49f9-b51f-935107e7b5aa', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '992d1a8d-b5f8-44fc-9a48-4b3e60a7b15e', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('40fff4ef-97f8-441e-adc3-6872db37dd73', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c30389e8-eb3e-4a0d-99c4-639e1893f50a', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('45afb550-8ce5-46eb-bc8d-310023725ac8', 'b95c69b7-84be-430f-ae57-27a703ae3998', '992d1a8d-b5f8-44fc-9a48-4b3e60a7b15e', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('46745b75-b3f8-4b61-9fc9-535cb5cb2a15', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('48b97e14-3e90-4fd1-87d7-625a32aad660', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '355f387f-a22b-4f8c-9cd6-ae10e930cd70', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('55cf5c38-a9ff-4685-b12f-8c558781c648', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'b180aafa-0d1a-4898-b838-bc20cd44356d', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('5633c975-a010-4e79-b2a7-6f5b9fa63486', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c038dc93-f30d-4802-a090-be352eab341a', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('5fa50c70-1741-4aa5-85e4-c61fde3463bc', '2d56198c-d14b-4d02-a625-7559815b62fb', '3ed79f23-90bf-4669-bc02-42ae392e75c1', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('648191dd-db07-4570-9627-dd41345fd746', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '8623c941-5746-4667-9fb8-76f6f5059788', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('6ed00e98-af37-49c4-acc1-f496036b91e7', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '783aedd8-5d93-46b6-8c6d-e4d3f0f3f466', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('72ac50bb-3d27-48dd-bda0-12faa1fd6d6d', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2073345f-7344-43fe-9084-b7add56da652', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('807aa5dd-d8d4-440e-9a85-224267c9abb8', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f2ff9320-c643-4c85-8b68-15f86d47b88b', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('84c8ea44-854b-4497-96c0-d2593bfa446e', 'b95c69b7-84be-430f-ae57-27a703ae3998', '1a2ec857-e775-4377-9fb7-e3c77738b3e5', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('87355ec0-6e70-464a-ae43-302adc9cd875', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2020-01-01 20:01:58');
INSERT INTO `sys_role_permission` VALUES ('88573338-5b04-40a9-b932-b7793f9b41dc', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'a390845b-a53d-4bc9-af5d-331c37f34e6f', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('890428f5-9910-408a-8173-d46370c4236d', '2d56198c-d14b-4d02-a625-7559815b62fb', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('8b72bfaf-cc63-4271-9646-d91263d13542', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '78f8e29a-cccd-49e5-ada7-5af40dd95312', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('8dde0149-ba66-46ed-9145-20efa5a33f96', '2d56198c-d14b-4d02-a625-7559815b62fb', '37101ed5-e840-4082-ae33-682ca6e41ad8', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('8f3f1282-8e8d-4be3-aa68-989e960bd28c', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'db2d31b7-fdcb-478e-bfde-a55eb8b0aa43', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('93e8eb0b-c35f-4bbb-bba4-beee27611e7b', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '4018e179-e599-41d0-bac5-c5408e1d4bc6', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('996ce823-09f7-43ac-8d88-17c16b7f7678', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3ed79f23-90bf-4669-bc02-42ae392e75c1', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('9b65792e-6e14-4dab-b0a4-58df7d4b1a55', '2d56198c-d14b-4d02-a625-7559815b62fb', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('9cd3ed0b-a29b-45e5-8af3-9aa9e1662901', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'b180aafa-0d1a-4898-b838-bc20cd44356d', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('9ef59f78-f2c5-42ca-a733-44d6c3a58a7f', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3c390dfd-0d9a-46de-9a5b-1ed884febcb2', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('b2379db3-09cd-41f7-acff-4dc7724036d8', 'b95c69b7-84be-430f-ae57-27a703ae3998', '355f387f-a22b-4f8c-9cd6-ae10e930cd70', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('b2ba9c26-03a6-41df-a6b5-bc52b5045d53', 'de54c167-e733-4b5b-83dd-ce10edd078f5', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2019-11-19 10:49:39');
INSERT INTO `sys_role_permission` VALUES ('b343074d-f7a7-4dde-b130-0ae098014265', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '817a58d1-ec82-4106-870a-bcc0bfaee0e7', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('b353be32-5661-4297-be53-f55fb8973eaa', 'b95c69b7-84be-430f-ae57-27a703ae3998', '475b4c24-40fa-4823-863a-ba6d793b7610', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('b5391a58-21fb-4744-8dc1-3dfc86f15996', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'b01614ab-0538-4cca-bb61-b46f18c60aa4', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('c34bb5b2-7c7f-442c-afad-a400e86b8df7', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('c3d2afff-d21b-40ae-ac52-d563879fcf24', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', 'c038dc93-f30d-4802-a090-be352eab341a', '2020-01-01 20:01:58');
INSERT INTO `sys_role_permission` VALUES ('cb9f69d2-f69d-44f8-b7ce-01211d5a3933', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '475b4c24-40fa-4823-863a-ba6d793b7610', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('cbfb8eb5-d44e-47d7-880a-45b976e93184', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('ce08c78d-518e-47e7-84e7-02a5baac18aa', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('d3928837-f0cb-407f-bf97-eb11019b31ae', '2d56198c-d14b-4d02-a625-7559815b62fb', '78f8e29a-cccd-49e5-ada7-5af40dd95312', '2020-01-01 19:59:46');
INSERT INTO `sys_role_permission` VALUES ('d3e419f0-6c81-4a58-a86e-48b61e003109', 'b95c69b7-84be-430f-ae57-27a703ae3998', '37101ed5-e840-4082-ae33-682ca6e41ad8', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('d6ddd178-0e8d-43c6-8916-abf363ffa39f', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'f2ff9320-c643-4c85-8b68-15f86d47b88b', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('dbc7f21e-6def-41be-a0e0-64b0673741d0', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('dd951c39-4493-492b-b77a-93247629f4f2', 'b95c69b7-84be-430f-ae57-27a703ae3998', '8623c941-5746-4667-9fb8-76f6f5059788', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('e35ecb61-5474-4e9c-afc9-0fd8ad26fb5d', 'b95c69b7-84be-430f-ae57-27a703ae3998', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('edad371b-820f-42e9-b88f-46dc1476edc6', 'b95c69b7-84be-430f-ae57-27a703ae3998', '58612968-d93c-4c21-8fdc-a825c0ab0275', '2019-11-09 21:25:31');
INSERT INTO `sys_role_permission` VALUES ('ee236557-a010-45d4-929a-32d61806bc99', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('f429c19a-44f0-4607-8b88-26ea4e5844b9', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f28b9215-3119-482d-bdc1-1f4c3f7c0869', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('f58d9e73-8929-4c90-a466-afc446339f97', 'de54c167-e733-4b5b-83dd-ce10edd078f5', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2019-11-19 10:49:39');
INSERT INTO `sys_role_permission` VALUES ('f9bd827e-2cfb-4047-bf3e-8ac64020759f', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f21ed5e8-0756-45dc-91c5-f58a9463caaa', '2019-11-09 22:46:24');
INSERT INTO `sys_role_permission` VALUES ('fdb8abac-a56a-44f3-801c-6aa8a6f594c0', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '0d99b687-3f46-4632-9d56-8dd5e476dae7', '2019-11-09 22:46:24');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1dfaafa7-fddf-46f2-b3d8-11bfe9ac7230', 'dev123', 'fa386978e2c04b7baef1', '9204993352a150ddd9febc421a5e5636', '131777777777', '4bd0b0a3-097d-4902-a1f7-641ea3b771bd', NULL, NULL, NULL, 1, 1, 1, NULL, 'fcf34b56-a7a2-4719-9236-867495e74c31', 1, '2019-11-09 22:47:30', '2020-03-18 08:54:08');
INSERT INTO `sys_user` VALUES ('1ff14b2c-d32b-496b-8fb7-d988b838b8e0', 'aitangbao', 'd3167e3c20314408845b', '934f8ba15eeb694c48474e6c50a1be56', '13231111111', 'a4f3e984-622b-4330-bcda-0ea01e44d299', NULL, NULL, NULL, 1, 1, 1, NULL, NULL, 1, '2020-03-18 08:10:24', NULL);
INSERT INTO `sys_user` VALUES ('5bc41939-78d9-40f8-a761-b9cf35f5d9e4', 'test', '7d69eec997034bfb8c5c', '', '13878888888', 'a4f3e984-622b-4330-bcda-0ea01e44d299', NULL, NULL, NULL, 2, 1, 1, NULL, 'fcf34b56-a7a2-4719-9236-867495e74c31', 1, '2020-01-01 20:01:20', '2020-01-01 20:03:27');
INSERT INTO `sys_user` VALUES ('7f8c0e32-058e-409d-8e7c-22a9afe6a0a0', 'zhangyang', '062f33e33afe4509b24b', '920f5d75077b25912e5054c4d58e0a4c', '13899999999', '4bd0b0a3-097d-4902-a1f7-641ea3b771bd', '张杨', NULL, '16399@163.com', 1, 1, 1, NULL, '7f8c0e32-058e-409d-8e7c-22a9afe6a0a0', 1, '2019-11-09 21:23:36', '2019-11-09 22:45:36');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('123', 'fcf34b56-a7a2-4719-9236-867495e74c31', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', NULL);
INSERT INTO `sys_user_role` VALUES ('33137e13-2318-42e4-a8e7-4bb7eea0f4ef', '1ff14b2c-d32b-496b-8fb7-d988b838b8e0', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2020-03-18 08:16:12');
INSERT INTO `sys_user_role` VALUES ('69d5bf3a-37a8-4da1-8941-fe44caa51af0', '7f8c0e32-058e-409d-8e7c-22a9afe6a0a0', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2019-11-09 21:25:49');
INSERT INTO `sys_user_role` VALUES ('fd1512dc-bf59-45b2-aa14-93da48ff0e8c', 'd860412c-9a4b-404b-8b71-ae8e3f4c27b7', 'de54c167-e733-4b5b-83dd-ce10edd078f5', '2019-11-19 10:35:14');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
