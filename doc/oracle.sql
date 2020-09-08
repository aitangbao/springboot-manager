-- 部门
CREATE TABLE sys_dept (
  id NUMBER(19, 0),
  dept_no varchar2(64),
  name varchar2(64),
  pid NUMBER(19, 0),
  status NUMBER(10, 0),
  relation_code varchar2(3000),
  dept_manager_id NUMBER(19, 0),
  manager_name varchar2(255),
  phone varchar2(20),
  create_time varchar2(20),
  update_time varchar2(20),
  deleted NUMBER(10, 0),
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_dept" IS '部门';

-- 日志
CREATE TABLE sys_log (
  id NUMBER(19, 0),
  user_id NUMBER(19, 0),
  username varchar2(50),
  operation varchar2(50),
  time NUMBER(10, 0),
  method varchar2(200),
  params varchar2(1000),
  ip varchar2(64),
  create_time varchar2(20),
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_log" IS '日志';

-- 菜单权限
CREATE TABLE sys_permission  (
  id NUMBER(19, 0) ,
  name varchar2(300),
  perms varchar2(500) ,
  icon varchar2(255),
  url varchar2(100),
  target varchar2(50),
  pid NUMBER(19, 0),
  order_num NUMBER(10, 0),
  type NUMBER(10, 0),
  status NUMBER(10, 0),
  create_time varchar2(20),
  update_time varchar2(20),
  deleted NUMBER(10, 0),
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_permission" IS '菜单权限';

-- 角色
CREATE TABLE sys_role (
  id NUMBER(19, 0),
  name varchar2(255),
  description varchar2(255),
  status NUMBER(10, 0),
  create_time varchar2(20),
  update_time varchar2(20),
  deleted NUMBER(10, 0),
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_role" IS '角色';

-- 角色权限关联表
CREATE TABLE sys_role_permission (
  id NUMBER(19, 0) ,
  role_id NUMBER(19, 0),
  permission_id NUMBER(19, 0),
  create_time varchar2(20),
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_role_permission" IS '角色权限关联表';


-- 用户
CREATE TABLE sys_user (
  id NUMBER(19, 0),
  username varchar2(50),
  salt varchar2(20),
  password varchar2(200),
  phone varchar2(20),
  dept_id NUMBER(19, 0),
  real_name varchar2(60),
  nick_name varchar2(60),
  email varchar2(50),
  status NUMBER(10, 0),
  sex NUMBER(10, 0),
  deleted NUMBER(10, 0),
  create_id NUMBER(19, 0),
  update_id NUMBER(19, 0),
  create_where NUMBER(10, 0) DEFAULT 1,
  create_time varchar2(20),
  update_time varchar2(20),
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_user" IS '用户';

-- 用户角色
CREATE TABLE sys_user_role (
  id NUMBER(19, 0),
  user_id NUMBER(19, 0),
  role_id NUMBER(19, 0),
  create_time varchar2(20),
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_user_role" IS '用户角色';


-- 数据字典
CREATE TABLE sys_dict  (
  id NUMBER(19, 0) ,
  name varchar2(100) ,
  remark varchar2(255) ,
  create_time varchar2(20),
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_dict" IS '数据字典';

-- 数据字典明细
CREATE TABLE sys_dict_detail  (
  id NUMBER(19, 0) ,
  label varchar2(255) ,
  value varchar2(255) ,
  sort NUMBER(10, 0) ,
  dict_id NUMBER(19, 0) ,
  create_time varchar2(20),
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_dict_detail" IS '数据字典明细';

-- 定时任务
CREATE TABLE sys_job  (
  id NUMBER(19, 0) ,
  bean_name varchar2(200),
  params varchar2(2000) ,
  cron_expression varchar2(100) ,
  status  NUMBER(10, 0),
  remark varchar2(255) ,
  create_time varchar2(20) ,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_job" IS '定时任务';

-- 定时任务日志
CREATE TABLE sys_job_log  (
  id NUMBER(19, 0) ,
  job_id NUMBER(19, 0) ,
  bean_name varchar2(200) ,
  params varchar2(1000) ,
  status NUMBER(10, 0),
  error NUMBER(10, 0),
  times NUMBER(10, 0) ,
  create_time varchar2(20) ,
  PRIMARY KEY (id)
 );
COMMENT ON TABLE "sys_job_log" IS '定时任务日志';

-- 文章管理
 CREATE TABLE sys_content  (
  id NUMBER(19, 0),
  title varchar2(255),
  type NUMBER(10, 0),
  content VARCHAR2(4000) ,
  create_time varchar2(20) ,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_content" IS '文章管理';

-- 文件管理
CREATE TABLE sys_files  (
  id NUMBER(19, 0) ,
  url varchar2(200),
  create_date varchar2(20) ,
  file_name varchar2(255),
  file_path varchar2(255) ,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_files" IS '文件管理';

-- records
INSERT INTO sys_user(id, username, salt, password, phone, dept_id, real_name, nick_name, email, status, sex, deleted, create_id, update_id, create_where, create_time, update_time) VALUES (1, 'admin', '324ce32d86224b00a02b', '2102b59a75ab87616b62d0b9432569d0', '13888888888', 1, '爱糖宝', '爱糖宝', 'yingxue@163.com', 1, 2, 1, NULL, 1, 3, '2019-09-22 19:38:05', '2020-03-18 09:15:22');
INSERT INTO sys_role(id, name, description, status, create_time, update_time, deleted) VALUES (1, '超级管理员', '拥有所有权限-不能删除', 1, '2019-11-01 19:26:29', '2020-07-31 14:04:32', 1);
INSERT INTO sys_user_role(id, user_id, role_id, create_time) VALUES (1, 1, 1, '2020-03-19 02:23:13');
INSERT INTO sys_dept(id, dept_no, name, pid, status, relation_code, dept_manager_id, manager_name, phone, create_time, update_time, deleted) VALUES (1, 'D000001', '总公司', 0, 1, 'D000001', NULL, '小李', '13888888888', '2019-11-07 22:43:33', NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (1, '删除', 'sysGenerator:delete', NULL, 'sysGenerator/delete', NULL, 15, 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (2, 'SQL 监控', '', '', '/druid/sql.html', '_self', 21, 98, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:59', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (3, '新增', 'sys:role:add', NULL, '/sys/role', NULL, 44, 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (4, '添加', 'sysGenerator:add', NULL, 'sysGenerator/add', NULL, 15, 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (5, '删除', 'sys:dept:deleted', NULL, '/sys/dept/*', NULL, 32, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (6, '接口管理', '', '', '/doc.html', '_blank', 21, 100, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:02', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (7, '列表', 'sys:log:list', NULL, '/sys/logs', NULL, 8, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (8, '日志管理', NULL, NULL, '/index/logs', '_self', 45, 97, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (9, '新增', 'sys:dept:add', NULL, '/sys/dept', NULL, 32, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (10, '赋予角色', 'sys:user:role:update', NULL, '/sys/user/roles/*', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (11, '菜单权限管理', NULL, NULL, '/index/menus', '_self', 42, 98, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (12, '列表', 'sys:dept:list', NULL, '/sys/depts', NULL, 32, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (13, '删除', 'sys:role:deleted', NULL, '/sys/role/*', NULL, 44, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (14, '定时任务立即执行', 'sysJob:run', NULL, '/sysJob/run', '_self', 50, 5, 3, 1, '2020-04-22 15:47:54', NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (15, '代码生成', NULL, NULL, '/index/sysGenerator', '_self', 45, 1, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (16, '列表', 'sysGenerator:list', NULL, 'sysGenerator/listByPage', NULL, 15, 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (17, '详情', 'sys:permission:detail', NULL, '/sys/permission/*', NULL, 11, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (18, '定时任务恢复', 'sysJob:resume', NULL, '/sysJob/resume', '_self', 50, 4, 3, 1, '2020-04-22 15:48:40', NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (19, '列表', 'sys:role:list', NULL, '/sys/roles', NULL, 44, 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (20, '修改', 'sysGenerator:update', NULL, 'sysGenerator/update', NULL, 15, 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (21, '其他', NULL, 'layui-icon-list', NULL, NULL, 0, 200, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (22, '详情', 'sys:dept:detail', NULL, '/sys/dept/*', NULL, 32, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (23, '列表', 'sys:user:list', NULL, '/sys/users', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (24, '用户管理', NULL, NULL, '/index/users', '_self', 42, 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (25, '详情', 'sys:user:detail', NULL, '/sys/user/*', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (26, '删除', 'sys:permission:deleted', NULL, '/sys/permission/*', NULL, 11, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (27, '更新', 'sys:role:update', NULL, '/sys/role', NULL, 44, 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (28, '系统信息', '', '', '/index/systemInfo', '_self', 21, 1, 2, 1, '2020-05-26 14:21:47', '2020-05-26 14:23:01', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (29, '更新', 'sys:dept:update', NULL, '/sys/dept', NULL, 32, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (30, '详情', 'sys:role:detail', NULL, '/sys/role/*', NULL, 44, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (31, '编辑', 'sys:permission:update', NULL, '/sys/permission', NULL, 11, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (32, '部门管理', NULL, NULL, '/index/depts', '_self', 42, 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (33, '新增', 'sys:user:add', NULL, '/sys/user', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (34, '列表', 'sys:permission:list', NULL, '/sys/permissions', NULL, 11, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (35, '新增', 'sys:permission:add', NULL, '/sys/permission', NULL, 11, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (36, '字典管理', NULL, '', '/index/sysDict', NULL, 45, 10, 2, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (37, '列表', 'sysDict:list', NULL, 'sysDict/listByPage', NULL, 36, 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (38, '新增', 'sysDict:add', NULL, 'sysDict/add', NULL, 36, 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (39, '修改', 'sysDict:update', NULL, 'sysDict/update', NULL, 36, 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (40, '删除', 'sysDict:delete', NULL, 'sysDict/delete', NULL, 36, 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (41, '表单构建', '', '', '/index/build', '_self', 21, 1, 2, 1, '2020-04-22 13:09:41', '2020-05-07 13:36:47', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (42, '组织管理', NULL, 'layui-icon-user', NULL, NULL, 0, 1, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (43, '拥有角色', 'sys:user:role:detail', NULL, '/sys/user/roles/*', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (44, '角色管理', NULL, NULL, '/index/roles', '_self', 42, 99, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (45, '系统管理', NULL, 'layui-icon-set-fill', NULL, NULL, 0, 98, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (46, '定时任务暂停', 'sysJob:pause', NULL, '/sysJob/pause', '_self', 50, 1, 3, 1, '2020-04-22 15:48:18', NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (47, '更新', 'sys:user:update', NULL, '/sys/user', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (48, '删除', 'sys:user:deleted', NULL, '/sys/user', NULL, 24, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (49, '删除', 'sys:log:deleted', NULL, '/sys/logs', NULL, 8, 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (50, '定时任务', NULL, NULL, '/index/sysJob', '_self', 45, 10, 2, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (51, '列表', 'sysJob:list', NULL, 'sysJob/listByPage', NULL, 50, 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (52, '新增', 'sysJob:add', NULL, 'sysJob/add', NULL, 50, 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (53, '修改', 'sysJob:update', NULL, 'sysJob/update', NULL, 50, 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES (54, '删除', 'sysJob:delete', NULL, 'sysJob/delete', NULL, 50, 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (1, 1, 1, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (2, 1, 2, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (3, 1, 3, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (4, 1, 4, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (5, 1, 5, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (6, 1, 6, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (7, 1, 7, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (8, 1, 8, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (9, 1, 9, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (10, 1, 10, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (11, 1, 11, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (12, 1, 12, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (13, 1, 13, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (14, 1, 14, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (15, 1, 15, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (16, 1, 16, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (17, 1, 17, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (18, 1, 18, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (19, 1, 19, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (20, 1, 20, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (21, 1, 21, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (22, 1, 22, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (23, 1, 23, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (24, 1, 24, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (25, 1, 25, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (26, 1, 26, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (27, 1, 27, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (28, 1, 28, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (29, 1, 29, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (30, 1, 30, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (31, 1, 31, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (32, 1, 32, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (33, 1, 33, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (34, 1, 34, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (35, 1, 35, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (36, 1, 36, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (37, 1, 37, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (38, 1, 38, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (39, 1, 39, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (40, 1, 40, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (41, 1, 41, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (42, 1, 42, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (43, 1, 43, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (44, 1, 44, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (45, 1, 45, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (46, 1, 46, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (47, 1, 47, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (48, 1, 48, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (49, 1, 49, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (50, 1, 50, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (51, 1, 51, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (52, 1, 52, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (53, 1, 53, '2020-07-31 14:04:32');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES (54, 1, 54, '2020-07-31 14:04:32');
INSERT INTO sys_dict(id, name, remark, create_time) VALUES (1, 'sex', '性别', '2020-04-30 17:24:09');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES (1, '男', '1', 1, 1, '2020-04-30 17:24:19');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES (2, '女', '2', 2, 1, '2020-04-30 17:24:25');
INSERT INTO sys_job(id, bean_name, params, cron_expression, status, remark, create_time)  VALUES (1, 'testTask', '1', '*/5 * * * * ?', 1, '1', '2020-04-22 16:58:35');
INSERT INTO sys_job(id, bean_name, params, cron_expression, status, remark, create_time)  VALUES (2, 'systemTask', '', '*/5 * * * * ?', 1, '服务器系统信息推送定时任务', '2020-08-27 17:05:49');
