-- 部门
CREATE TABLE sys_dept (
  id varchar2(64),
  dept_no varchar2(64),
  name varchar2(64),
  pid varchar2(64),
  status NUMBER(10, 0),
  relation_code varchar2(3000),
  dept_manager_id varchar2(64),
  manager_name varchar2(255),
  phone varchar2(20),
  create_time date,
  update_time date,
  deleted NUMBER(10, 0),
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_dept IS '部门';

-- 日志
CREATE TABLE sys_log (
  id varchar2(64),
  user_id varchar2(64),
  username varchar2(50),
  operation varchar2(50),
  time NUMBER(10, 0),
  method varchar2(200),
  params varchar2(1000),
  ip varchar2(64),
  create_time  date,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_log IS '日志';

-- 菜单权限
CREATE TABLE sys_permission  (
  id varchar2(64) ,
  name varchar2(300),
  perms varchar2(500) ,
  icon varchar2(255),
  url varchar2(100),
  target varchar2(50),
  pid varchar2(64),
  order_num NUMBER(10, 0),
  type NUMBER(10, 0),
  status NUMBER(10, 0),
  create_time  date,
  update_time  date,
  deleted NUMBER(10, 0),
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_permission IS '菜单权限';

-- 角色
CREATE TABLE sys_role (
  id varchar2(64),
  name varchar2(255),
  description varchar2(255),
  status NUMBER(10, 0),
  create_time  date,
  update_time  date,
  deleted NUMBER(10, 0),
  data_scope NUMBER(10, 0),
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_role IS '角色';

-- 角色权限关联表
CREATE TABLE sys_role_permission (
  id varchar2(64) ,
  role_id varchar2(64),
  permission_id varchar2(64),
  create_time  date,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_role_permission IS '角色权限关联表';

-- 角色部门关联表
CREATE TABLE sys_role_dept (
  id varchar2(64) ,
  role_id varchar2(64),
  dept_id varchar2(64),
  create_time  date,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_role_permission IS '角色部门关联表';


-- 用户
CREATE TABLE sys_user (
  id varchar2(64),
  username varchar2(50),
  salt varchar2(20),
  password varchar2(200),
  phone varchar2(20),
  dept_id varchar2(64),
  real_name varchar2(60),
  nick_name varchar2(60),
  email varchar2(50),
  status NUMBER(10, 0),
  sex NUMBER(10, 0),
  deleted NUMBER(10, 0),
  create_id varchar2(64),
  update_id varchar2(64),
  create_where NUMBER(10, 0) DEFAULT 1,
  create_time date,
  update_time date,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_user IS '用户';

-- 用户角色
CREATE TABLE sys_user_role (
  id varchar2(64),
  user_id varchar2(64),
  role_id varchar2(64),
  create_time  date,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_user_role IS '用户角色';


-- 数据字典
CREATE TABLE sys_dict  (
  id varchar2(64) ,
  name varchar2(100) ,
  remark varchar2(255) ,
  create_time  date,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_dict IS '数据字典';

-- 数据字典明细
CREATE TABLE sys_dict_detail  (
  id varchar2(50) ,
  label varchar2(255) ,
  value varchar2(255) ,
  sort NUMBER(10, 0) ,
  dict_id varchar2(50) ,
  create_time  date,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_dict_detail IS '数据字典明细';

-- 定时任务
CREATE TABLE sys_job  (
  id varchar2(50) ,
  bean_name varchar2(200),
  params varchar2(2000) ,
  cron_expression varchar2(100) ,
  status  NUMBER(10, 0),
  remark varchar2(255) ,
  create_time  date ,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_job IS '定时任务';

-- 定时任务日志
CREATE TABLE sys_job_log  (
  id varchar2(50) ,
  job_id varchar2(50) ,
  bean_name varchar2(200) ,
  params varchar2(1000) ,
  status NUMBER(10, 0),
  error NUMBER(10, 0),
  times NUMBER(10, 0) ,
  create_time  date ,
  PRIMARY KEY (id)
 );
COMMENT ON TABLE sys_job_log IS '定时任务日志';

-- 文章管理
 CREATE TABLE sys_content  (
  id varchar2(50),
  title varchar2(255),
  type NUMBER(10, 0),
  content VARCHAR2(4000) ,
  create_time  date ,
  create_id varchar2(50) ,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_content IS '文章管理';

-- 文件管理
CREATE TABLE sys_files  (
  id varchar2(50) ,
  url varchar2(200),
  create_date  date ,
  file_name varchar2(255),
  file_path varchar2(255) ,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_files IS '文件管理';


-- 初始数据
-- 初始数据
INSERT INTO sys_dept(id, dept_no, name, pid, status, relation_code, dept_manager_id, manager_name, phone, deleted) VALUES ('1', 'D000001', '总公司', '0', 1, 'D000001', NULL, '小李', '13888888888', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('1', '删除', 'sysGenerator:delete', NULL, 'sysGenerator/delete', NULL, '15', 1, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('10', '赋予角色', 'sys:user:role:update', NULL, '/sys/user/roles/*', NULL, '24', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('11', '菜单权限管理', NULL, NULL, '/index/menus', '_self', '51', 98, 2, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('12', '列表', 'sys:dept:list', NULL, '/sys/depts', NULL, '41', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('13', '删除', 'sys:role:deleted', NULL, '/sys/role/*', NULL, '53', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('14', '定时任务立即执行', 'sysJob:run', NULL, '/sysJob/run', '_self', '59', 5, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('15', '代码生成', NULL, NULL, '/index/sysGenerator', '_self', '54', 1, 2, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('16', '列表', 'sysGenerator:list', NULL, 'sysGenerator/listByPage', NULL, '15', 1, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('17', '详情', 'sys:permission:detail', NULL, '/sys/permission/*', NULL, '11', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('18', '定时任务恢复', 'sysJob:resume', NULL, '/sysJob/resume', '_self', '59', 4, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('19', '列表', 'sys:role:list', NULL, '/sys/roles', NULL, '53', 0, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('2', 'SQL 监控', '', '', '/druid/sql.html', '_self', '21', 98, 2, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('20', '修改', 'sysGenerator:update', NULL, 'sysGenerator/update', NULL, '15', 1, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('21', '其他', NULL, 'layui-icon-list', NULL, NULL, '0', 200, 1, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('22', '详情', 'sys:dept:detail', NULL, '/sys/dept/*', NULL, '41', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('23', '列表', 'sys:user:list', NULL, '/sys/users', NULL, '24', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('24', '用户管理', NULL, NULL, '/index/users', '_self', '51', 100, 2, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('25', '详情', 'sys:user:detail', NULL, '/sys/user/*', NULL, '24', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('26', '删除', 'sys:permission:deleted', NULL, '/sys/permission/*', NULL, '11', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('27', '文件管理', '', '', '/index/sysFiles', '_self', '54', 10, 2, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('28', '列表', 'sysFiles:list', NULL, 'sysFiles/listByPage', NULL, '27', 0, 3, 1,  1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('29', '新增', 'sysFiles:add', NULL, 'sysFiles/add', NULL, '27', 0, 3, 1,  1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('3', '新增', 'sys:role:add', NULL, '/sys/role', NULL, '53', 0, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('30', '删除', 'sysFiles:delete', NULL, 'sysFiles/delete', NULL, '27', 0, 3, 1,  1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('31', '文章管理', NULL, NULL, '/index/sysContent', '_self', '54', 10, 2, 1,  1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('32', '列表', 'sysContent:list', NULL, 'sysContent/listByPage', NULL, '31', 0, 3, 1,  1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('33', '新增', 'sysContent:add', NULL, 'sysContent/add', NULL, '31', 0, 3, 1,  1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('34', '修改', 'sysContent:update', NULL, 'sysContent/update', NULL, '31', 0, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('35', '删除', 'sysContent:delete', NULL, 'sysContent/delete', NULL, '31', 0, 3, 1,  1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('36', '更新', 'sys:role:update', NULL, '/sys/role', NULL, '53', 0, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('38', '更新', 'sys:dept:update', NULL, '/sys/dept', NULL, '41', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('39', '详情', 'sys:role:detail', NULL, '/sys/role/*', NULL, '53', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('4', '添加', 'sysGenerator:add', NULL, 'sysGenerator/add', NULL, '15', 1, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('40', '编辑', 'sys:permission:update', NULL, '/sys/permission', NULL, '11', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('41', '部门管理', NULL, NULL, '/index/depts', '_self', '51', 100, 2, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('42', '新增', 'sys:user:add', NULL, '/sys/user', NULL, '24', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('43', '列表', 'sys:permission:list', NULL, '/sys/permissions', NULL, '11', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('44', '新增', 'sys:permission:add', NULL, '/sys/permission', NULL, '11', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('45', '字典管理', NULL, '', '/index/sysDict', NULL, '54', 10, 2, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('46', '列表', 'sysDict:list', NULL, 'sysDict/listByPage', NULL, '45', 0, 3, 1,  1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('47', '新增', 'sysDict:add', NULL, 'sysDict/add', NULL, '45', 0, 3, 1,1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('48', '修改', 'sysDict:update', NULL, 'sysDict/update', NULL, '45', 0, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('49', '删除', 'sysDict:delete', NULL, 'sysDict/delete', NULL, '45', 0, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('5', '删除', 'sys:dept:deleted', NULL, '/sys/dept/*', NULL, '41', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('50', '表单构建', '', '', '/index/build', '_self', '21', 1, 2, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('51', '组织管理', NULL, 'layui-icon-user', NULL, NULL, '0', 1, 1, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('52', '拥有角色', 'sys:user:role:detail', NULL, '/sys/user/roles/*', NULL, '24', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('53', '角色管理', NULL, NULL, '/index/roles', '_self', '51', 99, 2, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('54', '系统管理', NULL, 'layui-icon-set-fill', NULL, NULL, '0', 98, 1, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('55', '定时任务暂停', 'sysJob:pause', NULL, '/sysJob/pause', '_self', '59', 1, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('56', '更新', 'sys:user:update', NULL, '/sys/user', NULL, '24', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('57', '删除', 'sys:user:deleted', NULL, '/sys/user', NULL, '24', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('58', '删除', 'sys:log:deleted', NULL, '/sys/logs', NULL, '8', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('59', '定时任务', NULL, NULL, '/index/sysJob', '_self', '54', 10, 2, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('6', '接口管理', '', '', '/doc.html', '_blank', '21', 100, 2, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('60', '列表', 'sysJob:list', NULL, 'sysJob/listByPage', NULL, '59', 0, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('61', '新增', 'sysJob:add', NULL, 'sysJob/add', NULL, '59', 0, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('62', '修改', 'sysJob:update', NULL, 'sysJob/update', NULL, '59', 0, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('63', '删除', 'sysJob:delete', NULL, 'sysJob/delete', NULL, '59', 0, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('7', '列表', 'sys:log:list', NULL, '/sys/logs', NULL, '8', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('8', '日志管理', NULL, NULL, '/index/logs', '_self', '54', 97, 2, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('9', '新增', 'sys:dept:add', NULL, '/sys/dept', NULL, '41', 100, 3, 1, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, deleted) VALUES ('1311115974068449281', '数据权限', 'sys:role:bindDept', '', '/sys/role/bindDept', '_self', '53', 5, 3, 1, 1);
INSERT INTO sys_role(id, name, description, status, deleted) VALUES ('1', '超级管理员', '拥有所有权限-不能删除', 1, 1);
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('1', '1', '1');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('10', '1', '10');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('11', '1', '11');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('12', '1', '12');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('13', '1', '13');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('14', '1', '14');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('15', '1', '15');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('16', '1', '16');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('17', '1', '17');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('18', '1', '18');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('19', '1', '19');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('2', '1', '2');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('20', '1', '20');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('21', '1', '21');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('22', '1', '22');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('23', '1', '23');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('24', '1', '24');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('25', '1', '25');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('26', '1', '26');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('27', '1', '27');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('28', '1', '28');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('29', '1', '29');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('3', '1', '3');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('30', '1', '30');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('31', '1', '31');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('32', '1', '32');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('33', '1', '33');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('34', '1', '34');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('35', '1', '35');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('36', '1', '36');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('38', '1', '38');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('39', '1', '39');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('4', '1', '4');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('40', '1', '40');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('41', '1', '41');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('42', '1', '42');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('43', '1', '43');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('44', '1', '44');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('45', '1', '45');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('46', '1', '46');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('47', '1', '47');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('48', '1', '48');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('49', '1', '49');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('5', '1', '5');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('50', '1', '50');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('51', '1', '51');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('52', '1', '52');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('53', '1', '53');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('54', '1', '54');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('55', '1', '55');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('56', '1', '56');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('57', '1', '57');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('58', '1', '58');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('59', '1', '59');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('6', '1', '6');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('60', '1', '60');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('61', '1', '61');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('62', '1', '62');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('63', '1', '63');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('7', '1', '7');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('8', '1', '8');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('9', '1', '9');
INSERT INTO sys_role_permission(id, role_id, permission_id) VALUES ('1311116066716430339', '1', '1311115974068449281');
INSERT INTO sys_user(id, username, salt, password, phone, dept_id, real_name, nick_name, email, status, sex, deleted, create_id, update_id, create_where) VALUES ('1', 'admin', '324ce32d86224b00a02b', '2102b59a75ab87616b62d0b9432569d0', '13888888888', '1', '爱糖宝', '爱糖宝', 'xxxxxx@163.com', 1, 2, 1, '1', '1', 3);
INSERT INTO sys_user_role(id, user_id, role_id) VALUES ('1', '1', '1');
INSERT INTO sys_dict(id, name, remark) VALUES ('1255790029680242690', 'sex', '性别');
INSERT INTO sys_dict(id, name, remark) VALUES ('1282504369620430849', 'content_type', '文章类型略略略');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id) VALUES ('1255790073535885314', '男', '1', 1, '1255790029680242690');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id) VALUES ('1255790100115189761', '女', '2', 2, '1255790029680242690');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id) VALUES ('1282504475715350530', '诗词', '1', 1, '1282504369620430849');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id) VALUES ('1282504651729317889', '散文', '2', 2, '1282504369620430849');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id) VALUES ('1282846022950842369', '剧本', '3', 3, '1282504369620430849');
INSERT INTO sys_job(id, bean_name, params, cron_expression, status, remark) VALUES ('1252884495040782337', 'testTask', '1', '0 */1 * * * ?', 0, '1');
