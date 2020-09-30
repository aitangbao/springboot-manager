-- 部门
CREATE TABLE sys_dept (
  id varchar(64),
  dept_no varchar(64),
  name varchar(64),
  pid varchar(64),
  status int,
  relation_code varchar(3000),
  dept_manager_id varchar(64),
  manager_name varchar(255),
  phone varchar(20),
  create_time datetime,
  update_time datetime,
  deleted int,
  PRIMARY KEY (id)
);

-- 日志
CREATE TABLE sys_log (
  id varchar(64),
  user_id varchar(64),
  username varchar(50),
  operation varchar(50),
  time int,
  method varchar(200),
  params varchar(1000),
  ip varchar(64),
  create_time datetime,
  PRIMARY KEY (id)
);


-- 菜单权限
CREATE TABLE sys_permission  (
  id varchar(64) ,
  name varchar(300),
  perms varchar(500) ,
  icon varchar(255),
  url varchar(100),
  target varchar(50),
  pid varchar(64),
  order_num int,
  type int,
  status int,
  create_time datetime,
  update_time datetime,
  deleted int,
  PRIMARY KEY (id)
);


-- 角色
CREATE TABLE sys_role (
  id varchar(64),
  name varchar(255),
  description varchar(255),
  status int,
  create_time datetime,
  update_time datetime,
  deleted int,
  data_scope int,
  PRIMARY KEY (id)
);

-- 角色部门关联表
CREATE TABLE sys_role_dept (
  id varchar(64) ,
  role_id varchar(64),
  dept_id varchar(64),
  create_time datetime,
  PRIMARY KEY (id)
);



-- 角色权限关联表
CREATE TABLE sys_role_permission (
  id varchar(64) ,
  role_id varchar(64),
  permission_id varchar(64),
  create_time datetime,
  PRIMARY KEY (id)
);


-- 用户
CREATE TABLE sys_user (
  id varchar(64),
  username varchar(50),
  salt varchar(20),
  password varchar(200),
  phone varchar(20),
  dept_id varchar(64),
  real_name varchar(60),
  nick_name varchar(60),
  email varchar(50),
  status int,
  sex int,
  deleted int,
  create_id varchar(64),
  update_id varchar(64),
  create_where int DEFAULT 1,
  create_time datetime,
  update_time datetime,
  PRIMARY KEY (id)
);


-- 用户角色
CREATE TABLE sys_user_role (
  id varchar(64),
  user_id varchar(64),
  role_id varchar(64),
  create_time datetime,
  PRIMARY KEY (id)
);

-- 数据字典
CREATE TABLE sys_dict  (
  id varchar(64) ,
  name varchar(100) ,
  remark varchar(255) ,
  create_time datetime,
  PRIMARY KEY (id)
);

-- 数据字典明细
CREATE TABLE sys_dict_detail  (
  id varchar(50) ,
  label varchar(255) ,
  value varchar(255) ,
  sort int ,
  dict_id varchar(50) ,
  create_time datetime,
  PRIMARY KEY (id)
);

-- 定时任务
CREATE TABLE sys_job  (
  id varchar(50) ,
  bean_name varchar(200),
  params varchar(2000) ,
  cron_expression varchar(100) ,
  status  int,
  remark varchar(255) ,
  create_time datetime ,
  PRIMARY KEY (id)
);

-- 定时任务日志
CREATE TABLE sys_job_log  (
  id varchar(50) ,
  job_id varchar(50) ,
  bean_name varchar(200) ,
  params varchar(1000) ,
  status int,
  error int,
  times int ,
  create_time datetime ,
  PRIMARY KEY (id)
 );


-- 文章管理
 CREATE TABLE sys_content  (
  id varchar(50),
  title varchar(255),
  type int,
  content varchar(4000) ,
  create_time datetime ,
  create_id varchar(50) ,
  PRIMARY KEY (id)
);

-- 文件管理
CREATE TABLE sys_files  (
  id varchar(50) ,
  url varchar(200),
  create_date datetime ,
  file_name varchar(255),
  file_path varchar(255) ,
  PRIMARY KEY (id)
);


-- 初始数据
-- 初始数据
INSERT INTO sys_dept(id, dept_no, name, pid, status, relation_code, dept_manager_id, manager_name, phone, create_time, update_time, deleted) VALUES ('1', 'D000001', '总公司', '0', 1, 'D000001', NULL, '小李', '13888888888', '2019-11-07 22:43:33', NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('1', '删除', 'sysGenerator:delete', NULL, 'sysGenerator/delete', NULL, '15', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('10', '赋予角色', 'sys:user:role:update', NULL, '/sys/user/roles/*', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('11', '菜单权限管理', NULL, NULL, '/index/menus', '_self', '51', 98, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('12', '列表', 'sys:dept:list', NULL, '/sys/depts', NULL, '41', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('13', '删除', 'sys:role:deleted', NULL, '/sys/role/*', NULL, '53', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('14', '定时任务立即执行', 'sysJob:run', NULL, '/sysJob/run', '_self', '59', 5, 3, 1, '2020-04-22 15:47:54', NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('15', '代码生成', NULL, NULL, '/index/sysGenerator', '_self', '54', 1, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('16', '列表', 'sysGenerator:list', NULL, 'sysGenerator/listByPage', NULL, '15', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('17', '详情', 'sys:permission:detail', NULL, '/sys/permission/*', NULL, '11', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('18', '定时任务恢复', 'sysJob:resume', NULL, '/sysJob/resume', '_self', '59', 4, 3, 1, '2020-04-22 15:48:40', NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('19', '列表', 'sys:role:list', NULL, '/sys/roles', NULL, '53', 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('2', 'SQL 监控', '', '', '/druid/sql.html', '_self', '21', 98, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:59', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('20', '修改', 'sysGenerator:update', NULL, 'sysGenerator/update', NULL, '15', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('21', '其他', NULL, 'layui-icon-list', NULL, NULL, '0', 200, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('22', '详情', 'sys:dept:detail', NULL, '/sys/dept/*', NULL, '41', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('23', '列表', 'sys:user:list', NULL, '/sys/users', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('24', '用户管理', NULL, NULL, '/index/users', '_self', '51', 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('25', '详情', 'sys:user:detail', NULL, '/sys/user/*', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('26', '删除', 'sys:permission:deleted', NULL, '/sys/permission/*', NULL, '11', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('27', '文件管理', '', '', '/index/sysFiles', '_self', '54', 10, 2, 1, NULL, '2020-06-15 16:00:29', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('28', '列表', 'sysFiles:list', NULL, 'sysFiles/listByPage', NULL, '27', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('29', '新增', 'sysFiles:add', NULL, 'sysFiles/add', NULL, '27', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('3', '新增', 'sys:role:add', NULL, '/sys/role', NULL, '53', 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('30', '删除', 'sysFiles:delete', NULL, 'sysFiles/delete', NULL, '27', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('31', '文章管理', NULL, NULL, '/index/sysContent', '_self', '54', 10, 2, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('32', '列表', 'sysContent:list', NULL, 'sysContent/listByPage', NULL, '31', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('33', '新增', 'sysContent:add', NULL, 'sysContent/add', NULL, '31', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('34', '修改', 'sysContent:update', NULL, 'sysContent/update', NULL, '31', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('35', '删除', 'sysContent:delete', NULL, 'sysContent/delete', NULL, '31', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('36', '更新', 'sys:role:update', NULL, '/sys/role', NULL, '53', 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('38', '更新', 'sys:dept:update', NULL, '/sys/dept', NULL, '41', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('39', '详情', 'sys:role:detail', NULL, '/sys/role/*', NULL, '53', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('4', '添加', 'sysGenerator:add', NULL, 'sysGenerator/add', NULL, '15', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('40', '编辑', 'sys:permission:update', NULL, '/sys/permission', NULL, '11', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('41', '部门管理', NULL, NULL, '/index/depts', '_self', '51', 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('42', '新增', 'sys:user:add', NULL, '/sys/user', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('43', '列表', 'sys:permission:list', NULL, '/sys/permissions', NULL, '11', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('44', '新增', 'sys:permission:add', NULL, '/sys/permission', NULL, '11', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('45', '字典管理', NULL, '', '/index/sysDict', NULL, '54', 10, 2, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('46', '列表', 'sysDict:list', NULL, 'sysDict/listByPage', NULL, '45', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('47', '新增', 'sysDict:add', NULL, 'sysDict/add', NULL, '45', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('48', '修改', 'sysDict:update', NULL, 'sysDict/update', NULL, '45', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('49', '删除', 'sysDict:delete', NULL, 'sysDict/delete', NULL, '45', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('5', '删除', 'sys:dept:deleted', NULL, '/sys/dept/*', NULL, '41', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('50', '表单构建', '', '', '/index/build', '_self', '21', 1, 2, 1, '2020-04-22 13:09:41', '2020-05-07 13:36:47', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('51', '组织管理', NULL, 'layui-icon-user', NULL, NULL, '0', 1, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('52', '拥有角色', 'sys:user:role:detail', NULL, '/sys/user/roles/*', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('53', '角色管理', NULL, NULL, '/index/roles', '_self', '51', 99, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('54', '系统管理', NULL, 'layui-icon-set-fill', NULL, NULL, '0', 98, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('55', '定时任务暂停', 'sysJob:pause', NULL, '/sysJob/pause', '_self', '59', 1, 3, 1, '2020-04-22 15:48:18', NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('56', '更新', 'sys:user:update', NULL, '/sys/user', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('57', '删除', 'sys:user:deleted', NULL, '/sys/user', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('58', '删除', 'sys:log:deleted', NULL, '/sys/logs', NULL, '8', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('59', '定时任务', NULL, NULL, '/index/sysJob', '_self', '54', 10, 2, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('6', '接口管理', '', '', '/doc.html', '_blank', '21', 100, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:02', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('60', '列表', 'sysJob:list', NULL, 'sysJob/listByPage', NULL, '59', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('61', '新增', 'sysJob:add', NULL, 'sysJob/add', NULL, '59', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('62', '修改', 'sysJob:update', NULL, 'sysJob/update', NULL, '59', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('63', '删除', 'sysJob:delete', NULL, 'sysJob/delete', NULL, '59', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('7', '列表', 'sys:log:list', NULL, '/sys/logs', NULL, '8', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('8', '日志管理', NULL, NULL, '/index/logs', '_self', '54', 97, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('9', '新增', 'sys:dept:add', NULL, '/sys/dept', NULL, '41', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('1311115974068449281', '数据权限', 'sys:role:bindDept', '', '/sys/role/bindDept', '_self', '53', 5, 3, 1, '2020-09-30 09:29:42', NULL, 1);
INSERT INTO sys_role(id, name, description, status, create_time, update_time, deleted) VALUES ('1', '超级管理员', '拥有所有权限-不能删除', 1, '2019-11-01 19:26:29', '2020-03-19 13:29:51', 1);
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('1', '1', '1', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('10', '1', '10', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('11', '1', '11', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('12', '1', '12', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('13', '1', '13', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('14', '1', '14', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('15', '1', '15', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('16', '1', '16', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('17', '1', '17', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('18', '1', '18', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('19', '1', '19', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('2', '1', '2', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('20', '1', '20', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('21', '1', '21', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('22', '1', '22', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('23', '1', '23', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('24', '1', '24', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('25', '1', '25', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('26', '1', '26', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('27', '1', '27', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('28', '1', '28', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('29', '1', '29', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('3', '1', '3', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('30', '1', '30', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('31', '1', '31', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('32', '1', '32', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('33', '1', '33', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('34', '1', '34', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('35', '1', '35', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('36', '1', '36', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('38', '1', '38', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('39', '1', '39', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('4', '1', '4', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('40', '1', '40', '2020-06-15 15:21:17');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('41', '1', '41', '2020-06-15 15:21:17');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('42', '1', '42', '2020-06-15 15:21:17');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('43', '1', '43', '2020-06-15 15:21:17');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('44', '1', '44', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('45', '1', '45', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('46', '1', '46', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('47', '1', '47', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('48', '1', '48', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('49', '1', '49', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('5', '1', '5', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('50', '1', '50', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('51', '1', '51', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('52', '1', '52', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('53', '1', '53', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('54', '1', '54', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('55', '1', '55', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('56', '1', '56', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('57', '1', '57', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('58', '1', '58', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('59', '1', '59', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('6', '1', '6', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('60', '1', '60', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('61', '1', '61', '2020-05-26 14:21:56');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('62', '1', '62', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('63', '1', '63', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('7', '1', '7', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('8', '1', '8', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('9', '1', '9', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('1311116066716430339', '1', '1311115974068449281', '2020-09-30 09:30:04');
INSERT INTO sys_user(id, username, salt, password, phone, dept_id, real_name, nick_name, email, status, sex, deleted, create_id, update_id, create_where, create_time, update_time) VALUES ('1', 'admin', '324ce32d86224b00a02b', '2102b59a75ab87616b62d0b9432569d0', '13888888888', '1', '爱糖宝', '爱糖宝', 'xxxxxx@163.com', 1, 2, 1, '1', '1', 3, '2019-09-22 19:38:05', '2020-03-18 09:15:22');
INSERT INTO sys_user_role(id, user_id, role_id, create_time) VALUES ('1', '1', '1', '2020-03-19 02:23:13');
INSERT INTO sys_dict(id, name, remark, create_time) VALUES ('1255790029680242690', 'sex', '性别', '2020-04-30 17:24:09');
INSERT INTO sys_dict(id, name, remark, create_time) VALUES ('1282504369620430849', 'content_type', '文章类型略略略', '2020-07-13 10:37:24');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES ('1255790073535885314', '男', '1', 1, '1255790029680242690', '2020-04-30 17:24:19');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES ('1255790100115189761', '女', '2', 2, '1255790029680242690', '2020-04-30 17:24:25');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES ('1282504475715350530', '诗词', '1', 1, '1282504369620430849', '2020-07-13 10:37:49');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES ('1282504651729317889', '散文', '2', 2, '1282504369620430849', '2020-07-13 10:38:31');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES ('1282846022950842369', '剧本', '3', 3, '1282504369620430849', '2020-07-14 09:15:01');
INSERT INTO sys_job(id, bean_name, params, cron_expression, status, remark, create_time) VALUES ('1252884495040782337', 'testTask', '1', '0 */1 * * * ?', 0, '1', '2020-04-22 16:58:35');
