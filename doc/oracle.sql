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
  create_time timestamp,
  update_time timestamp,
  deleted NUMBER(10, 0),
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_dept" IS '部门';

-- 代码生成
CREATE TABLE sys_generator (
  id varchar2(64),
  table_name varchar2(255),
  menu_name varchar2(255),
  pid varchar2(64),
  gen_time timestamp,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_generator" IS '代码生成';

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
  create_time timestamp,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_log" IS '日志';

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
  create_time timestamp,
  update_time timestamp,
  deleted NUMBER(10, 0),
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_permission" IS '菜单权限';

-- 角色
CREATE TABLE sys_role (
  id varchar2(64),
  name varchar2(255),
  description varchar2(255),
  status NUMBER(10, 0),
  create_time timestamp,
  update_time timestamp,
  deleted NUMBER(10, 0),
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_role" IS '角色';

-- 角色权限关联表
CREATE TABLE sys_role_permission (
  id varchar2(64) ,
  role_id varchar2(64),
  permission_id varchar2(64),
  create_time timestamp,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_role_permission" IS '角色权限关联表';


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
  create_time timestamp,
  update_time timestamp,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_user" IS '用户';

-- 用户角色
CREATE TABLE sys_user_role (
  id varchar2(64),
  user_id varchar2(64),
  role_id varchar2(64),
  create_time timestamp,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_user_role" IS '用户角色';


-- 数据字典
CREATE TABLE sys_dict  (
  id varchar2(64) ,
  name varchar2(100) ,
  remark varchar2(255) ,
  create_time timestamp,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_dict" IS '数据字典';

-- 数据字典明细
CREATE TABLE sys_dict_detail  (
  id varchar2(50) ,
  label varchar2(255) ,
  value varchar2(255) ,
  sort NUMBER(10, 0) ,
  dict_id varchar2(50) ,
  create_time timestamp,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_dict_detail" IS '数据字典明细';

-- 定时任务
CREATE TABLE sys_job  (
  id varchar2(50) ,
  bean_name varchar2(200),
  params varchar2(2000) ,
  cron_expression varchar2(100) ,
  status  NUMBER(10, 0),
  remark varchar2(255) ,
  create_time timestamp ,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_job" IS '定时任务';

-- 定时任务日志
CREATE TABLE sys_job_log  (
  id varchar2(50) ,
  job_id varchar2(50) ,
  bean_name varchar2(200) ,
  params varchar2(1000) ,
  status NUMBER(10, 0),
  error NUMBER(10, 0),
  times NUMBER(10, 0) ,
  create_time timestamp ,
  PRIMARY KEY (id)
 );
COMMENT ON TABLE "sys_job_log" IS '定时任务日志';

-- 文章管理
 CREATE TABLE sys_content  (
  id varchar2(50),
  title varchar2(255),
  content VARCHAR2(4000) ,
  create_time timestamp ,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_content" IS '文章管理';

-- 文件管理
CREATE TABLE sys_files  (
  id varchar2(50) ,
  url varchar2(200),
  create_date timestamp ,
  file_name varchar2(255),
  file_path varchar2(255) ,
  PRIMARY KEY (id)
);
COMMENT ON TABLE "sys_files" IS '文件管理';


-- 初始数据
INSERT INTO sys_dept(id, dept_no, name, pid, status, relation_code, dept_manager_id, manager_name, phone, create_time, update_time, deleted) VALUES ('4bd0b0a3-097d-4902-a1f7-641ea3b771bd', 'D000002', '部门1', '72a4f388-50f8-4019-8c67-530cd7c74e7a', 1, 'D000001D000002', NULL, '张杨', '13899999999', '2019-11-05 12:24:50', '2019-11-09 21:22:55', 1);
INSERT INTO sys_dept(id, dept_no, name, pid, status, relation_code, dept_manager_id, manager_name, phone, create_time, update_time, deleted) VALUES ('72a4f388-50f8-4019-8c67-530cd7c74e7a', 'D000001', '总公司', '0', 1, 'D000001', NULL, '小李', '13888888888', '2019-11-07 22:43:33', NULL, 1);

INSERT INTO sys_dict(id, name, remark, create_time) VALUES ('1255790029680242690', 'sex', '性别', '2020-04-30 17:24:09');

INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES ('1255790073535885314', '男', '1', 1, '1255790029680242690', '2020-04-30 17:24:19');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES ('1255790100115189761', '女', '2', 2, '1255790029680242690', '2020-04-30 17:24:25');

INSERT INTO sys_generator(id, table_name, menu_name, pid, gen_time) VALUES ('1', 'sys_dept', '部门管理', '0', '2020-03-20 13:16:47');
INSERT INTO sys_generator(id, table_name, menu_name, pid, gen_time) VALUES ('2', 'sys_generator', '代码生成', '0', '2020-03-20 13:16:47');
INSERT INTO sys_generator(id, table_name, menu_name, pid, gen_time) VALUES ('3', 'sys_log', '日志管理', '0', '2020-03-20 13:16:47');
INSERT INTO sys_generator(id, table_name, menu_name, pid, gen_time) VALUES ('4', 'sys_permission', '权限管理', '0', '2020-03-20 13:16:47');
INSERT INTO sys_generator(id, table_name, menu_name, pid, gen_time) VALUES ('5', 'sys_role', '角色管理', '0', '2020-03-20 13:16:47');
INSERT INTO sys_generator(id, table_name, menu_name, pid, gen_time) VALUES ('6', 'sys_role_permission', '角色权限管理', '0', '2020-03-20 13:16:47');
INSERT INTO sys_generator(id, table_name, menu_name, pid, gen_time) VALUES ('7', 'sys_user', '用户管理', '0', '2020-03-20 13:16:47');
INSERT INTO sys_generator(id, table_name, menu_name, pid, gen_time) VALUES ('8', 'sys_user_role', '用户角色管理', '0', '2020-03-20 13:16:47');

INSERT INTO sys_job(id, bean_name, params, cron_expression, status, remark, create_time) VALUES ('1252884495040782337', 'testTask', '1', '0 */1 * * * ?', 0, '1', '2020-04-22 16:58:35');

INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('010bcf8b-3172-4331-9941-25788ca8cbb0', '删除', 'sysGenerator:delete', NULL, 'sysGenerator/delete', NULL, '43d1bbf7-fc4a-4a10-9ad1-205b16c7c05f', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('0d99b687-3f46-4632-9d56-8dd5e476dae7', 'SQL 监控', '', '', '/druid/sql.html', '_self', '65bdb02f47b94e71c2a2b9e459bde909', 98, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:59', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('1a2ec857-e775-4377-9fb7-e3c77738b3e5', '新增', 'sys:role:add', NULL, '/sys/role', NULL, 'e0b16b95-09de-4d60-a283-1eebd424ed47', 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('1dec779d-a9ec-448a-9389-a2b4eefce119', '添加', 'sysGenerator:add', NULL, 'sysGenerator/add', NULL, '43d1bbf7-fc4a-4a10-9ad1-205b16c7c05f', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('2073345f-7344-43fe-9084-b7add56da652', '删除', 'sys:dept:deleted', NULL, '/sys/dept/*', NULL, 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('26764d88-1d90-402d-b355-a75deef116f2', '接口管理', '', '', '/doc.html', '_blank', '65bdb02f47b94e71c2a2b9e459bde909', 100, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:02', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('355f387f-a22b-4f8c-9cd6-ae10e930cd70', '列表', 'sys:log:list', NULL, '/sys/logs', NULL, '37101ed5-e840-4082-ae33-682ca6e41ad8', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('37101ed5-e840-4082-ae33-682ca6e41ad8', '日志管理', NULL, NULL, '/index/logs', '_self', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 97, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('3a93a7e3-956a-408e-b2e4-108e9ece8f04', '新增', 'sys:dept:add', NULL, '/sys/dept', NULL, 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('3c390dfd-0d9a-46de-9a5b-1ed884febcb2', '赋予角色', 'sys:user:role:update', NULL, '/sys/user/roles/*', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('3dac936c-c4e1-4560-ac93-905502f61ae0', '菜单权限管理', NULL, NULL, '/index/menus', '_self', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 98, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('3ed79f23-90bf-4669-bc02-42ae392e75c1', '列表', 'sys:dept:list', NULL, '/sys/depts', NULL, 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('4018e179-e599-41d0-bac5-c5408e1d4bc6', '删除', 'sys:role:deleted', NULL, '/sys/role/*', NULL, 'e0b16b95-09de-4d60-a283-1eebd424ed47', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('438641da495235106c2d311703ac4290', '定时任务立即执行', 'sysJob:run', NULL, '/sysJob/run', '_self', 'fc9ca351846111eaab15b4a9fc2c4442', 5, 3, 1, '2020-04-22 15:47:54', NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('43d1bbf7-fc4a-4a10-9ad1-205b16c7c05f', '代码生成', NULL, NULL, '/index/sysGenerator', '_self', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 1, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('46dbb867-51d6-4523-852f-b12e75b34e3d', '列表', 'sysGenerator:list', NULL, 'sysGenerator/listByPage', NULL, '43d1bbf7-fc4a-4a10-9ad1-205b16c7c05f', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('475b4c24-40fa-4823-863a-ba6d793b7610', '详情', 'sys:permission:detail', NULL, '/sys/permission/*', NULL, '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('526be97ba24c5a1b3418cdf7ca176b7e', '定时任务恢复', 'sysJob:resume', NULL, '/sysJob/resume', '_self', 'fc9ca351846111eaab15b4a9fc2c4442', 4, 3, 1, '2020-04-22 15:48:40', NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('58612968-d93c-4c21-8fdc-a825c0ab0275', '列表', 'sys:role:list', NULL, '/sys/roles', NULL, 'e0b16b95-09de-4d60-a283-1eebd424ed47', 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('60c3443a-5ec9-4ea7-9484-d2870af93059', '修改', 'sysGenerator:update', NULL, 'sysGenerator/update', NULL, '43d1bbf7-fc4a-4a10-9ad1-205b16c7c05f', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('65bdb02f47b94e71c2a2b9e459bde909', '其他', NULL, 'layui-icon-list', NULL, NULL, '0', 200, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('761db494-833d-4a6c-94b4-3a7409fd9a78', '详情', 'sys:dept:detail', NULL, '/sys/dept/*', NULL, 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('783aedd8-5d93-46b6-8c6d-e4d3f0f3f466', '列表', 'sys:user:list', NULL, '/sys/users', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('78f8e29a-cccd-49e5-ada7-5af40dd95312', '用户管理', NULL, NULL, '/index/users', '_self', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('817a58d1-ec82-4106-870a-bcc0bfaee0e7', '详情', 'sys:user:detail', NULL, '/sys/user/*', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('8623c941-5746-4667-9fb8-76f6f5059788', '删除', 'sys:permission:deleted', NULL, '/sys/permission/*', NULL, '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('88fd1359aa3911ea883eb4a9fc2c4442', '文件管理', '', '', '/index/sysFiles', '_self', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 10, 2, 1, NULL, '2020-06-15 16:00:29', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('88fd59f1aa3911ea883eb4a9fc2c4442', '列表', 'sysFiles:list', NULL, 'sysFiles/listByPage', NULL, '88fd1359aa3911ea883eb4a9fc2c4442', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('88fd953aaa3911ea883eb4a9fc2c4442', '新增', 'sysFiles:add', NULL, 'sysFiles/add', NULL, '88fd1359aa3911ea883eb4a9fc2c4442', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('88fded61aa3911ea883eb4a9fc2c4442', '删除', 'sysFiles:delete', NULL, 'sysFiles/delete', NULL, '88fd1359aa3911ea883eb4a9fc2c4442', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('8cbcf9939f2f11ea9b7bb4a9fc2c4442', '文章管理', NULL, NULL, '/index/sysContent', '_self', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 10, 2, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('8cbd63619f2f11ea9b7bb4a9fc2c4442', '列表', 'sysContent:list', NULL, 'sysContent/listByPage', NULL, '8cbcf9939f2f11ea9b7bb4a9fc2c4442', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('8cbdc0cc9f2f11ea9b7bb4a9fc2c4442', '新增', 'sysContent:add', NULL, 'sysContent/add', NULL, '8cbcf9939f2f11ea9b7bb4a9fc2c4442', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('8cbdf07f9f2f11ea9b7bb4a9fc2c4442', '修改', 'sysContent:update', NULL, 'sysContent/update', NULL, '8cbcf9939f2f11ea9b7bb4a9fc2c4442', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('8cbe19f09f2f11ea9b7bb4a9fc2c4442', '删除', 'sysContent:delete', NULL, 'sysContent/delete', NULL, '8cbcf9939f2f11ea9b7bb4a9fc2c4442', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('992d1a8d-b5f8-44fc-9a48-4b3e60a7b15e', '更新', 'sys:role:update', NULL, '/sys/role', NULL, 'e0b16b95-09de-4d60-a283-1eebd424ed47', 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('9b02bb94c01d69b8dabeaeae1c85bbee', '系统信息', '', '', '/index/systemInfo', '_self', '65bdb02f47b94e71c2a2b9e459bde909', 1, 2, 1, '2020-05-26 14:21:47', '2020-05-26 14:23:01', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('a390845b-a53d-4bc9-af5d-331c37f34e6f', '更新', 'sys:dept:update', NULL, '/sys/dept', NULL, 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('b01614ab-0538-4cca-bb61-b46f18c60aa4', '详情', 'sys:role:detail', NULL, '/sys/role/*', NULL, 'e0b16b95-09de-4d60-a283-1eebd424ed47', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('b180aafa-0d1a-4898-b838-bc20cd44356d', '编辑', 'sys:permission:update', NULL, '/sys/permission', NULL, '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('c038dc93-f30d-4802-a090-be352eab341a', '部门管理', NULL, NULL, '/index/depts', '_self', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('c0a84726-47d8-4d7a-8d53-0736a4586647', '新增', 'sys:user:add', NULL, '/sys/user', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('c30389e8-eb3e-4a0d-99c4-639e1893a05f', '列表', 'sys:permission:list', NULL, '/sys/permissions', NULL, '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('c30389e8-eb3e-4a0d-99c4-639e1893f50a', '新增', 'sys:permission:add', NULL, '/sys/permission', NULL, '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('c69c2d5c81e711ea81a800163e045bb0', '字典管理', NULL, '', '/index/sysDict', NULL, 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 10, 2, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('c6a0d80a81e711ea81a800163e045bb0', '列表', 'sysDict:list', NULL, 'sysDict/listByPage', NULL, 'c69c2d5c81e711ea81a800163e045bb0', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('c6a36ae381e711ea81a800163e045bb0', '新增', 'sysDict:add', NULL, 'sysDict/add', NULL, 'c69c2d5c81e711ea81a800163e045bb0', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('c6a5e38d81e711ea81a800163e045bb0', '修改', 'sysDict:update', NULL, 'sysDict/update', NULL, 'c69c2d5c81e711ea81a800163e045bb0', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('c6a85a8381e711ea81a800163e045bb0', '删除', 'sysDict:delete', NULL, 'sysDict/delete', NULL, 'c69c2d5c81e711ea81a800163e045bb0', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('d372f1a482e69165151a7dbab0937903', '表单构建', '', '', '/index/build', '_self', '65bdb02f47b94e71c2a2b9e459bde909', 1, 2, 1, '2020-04-22 13:09:41', '2020-05-07 13:36:47', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('d6214dcb-8b6d-494b-88fa-f519fc08ff8f', '组织管理', NULL, 'layui-icon-user', NULL, NULL, '0', 1, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('db2d31b7-fdcb-478e-bfde-a55eb8b0aa43', '拥有角色', 'sys:user:role:detail', NULL, '/sys/user/roles/*', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('e0b16b95-09de-4d60-a283-1eebd424ed47', '角色管理', NULL, NULL, '/index/roles', '_self', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 99, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', '系统管理', NULL, 'layui-icon-set-fill', NULL, NULL, '0', 98, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('eef2b95e5d5ae2ebbb9d835e8850fd1f', '定时任务暂停', 'sysJob:pause', NULL, '/sysJob/pause', '_self', 'fc9ca351846111eaab15b4a9fc2c4442', 1, 3, 1, '2020-04-22 15:48:18', NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('f21ed5e8-0756-45dc-91c5-f58a9463caaa', '更新', 'sys:user:update', NULL, '/sys/user', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('f28b9215-3119-482d-bdc1-1f4c3f7c0869', '删除', 'sys:user:deleted', NULL, '/sys/user', NULL, '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('f2ff9320-c643-4c85-8b68-15f86d47b88b', '删除', 'sys:log:deleted', NULL, '/sys/logs', NULL, '37101ed5-e840-4082-ae33-682ca6e41ad8', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('fc9ca351846111eaab15b4a9fc2c4442', '定时任务', NULL, NULL, '/index/sysJob', '_self', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', 10, 2, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('fc9d3120846111eaab15b4a9fc2c4442', '列表', 'sysJob:list', NULL, 'sysJob/listByPage', NULL, 'fc9ca351846111eaab15b4a9fc2c4442', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('fc9d5be5846111eaab15b4a9fc2c4442', '新增', 'sysJob:add', NULL, 'sysJob/add', NULL, 'fc9ca351846111eaab15b4a9fc2c4442', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('fc9da4f7846111eaab15b4a9fc2c4442', '修改', 'sysJob:update', NULL, 'sysJob/update', NULL, 'fc9ca351846111eaab15b4a9fc2c4442', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission(id, name, perms, icon, url, target, pid, order_num, type, status, create_time, update_time, deleted) VALUES ('fc9dc9cd846111eaab15b4a9fc2c4442', '删除', 'sysJob:delete', NULL, 'sysJob/delete', NULL, 'fc9ca351846111eaab15b4a9fc2c4442', 0, 3, 1, NULL, NULL, 1);

INSERT INTO sys_role(id, name, description, status, create_time, update_time, deleted) VALUES ('11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '超级管理员', '拥有所有权限-不能删除', 1, '2019-11-01 19:26:29', '2020-03-19 13:29:51', 1);
INSERT INTO sys_role(id, name, description, status, create_time, update_time, deleted) VALUES ('2d56198c-d14b-4d02-a625-7559815b62fb', '普通用户角色', '只读', 1, '2019-11-09 22:49:18', '2020-01-01 19:59:46', 1);
INSERT INTO sys_role(id, name, description, status, create_time, update_time, deleted) VALUES ('8dd881c7-078c-406a-9c5f-242ab9ecfcb5', 'test', '测试', 1, '2020-01-01 20:01:58', '2019-11-19 10:43:05', 1);
INSERT INTO sys_role(id, name, description, status, create_time, update_time, deleted) VALUES ('b95c69b7-84be-430f-ae57-27a703ae3998', '后台管理员', '一般是程序员拥有，用来初始化菜单权限', 1, '2019-11-09 21:25:31', NULL, 1);
INSERT INTO sys_role(id, name, description, status, create_time, update_time, deleted) VALUES ('de54c167-e733-4b5b-83dd-ce10edd078f5', '超级权限', '', 1, '2019-11-19 10:34:48', '2019-11-19 10:49:39', 1);

INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('01201760169aa7c9936e46c0d5cd15f7', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '8623c941-5746-4667-9fb8-76f6f5059788', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('0214adfed9bbfe0269e72b86991c6d61', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c0a84726-47d8-4d7a-8d53-0736a4586647', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('0aa6eb812a915674e4009e0086147359', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '46dbb867-51d6-4523-852f-b12e75b34e3d', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('0bb947bcc52b8c2de194a0d7a58c9ca7', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '475b4c24-40fa-4823-863a-ba6d793b7610', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('0f8b0b9ed115cbcd150dc6838bf67703', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c6a5e38d81e711ea81a800163e045bb0', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('1620bc21c4ca152b0c1e4149134692bc', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('22850e325d6866065ec21ba5f4f25953', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('257a4679068332747600f171e1e37a46', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3ed79f23-90bf-4669-bc02-42ae392e75c1', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('25acec16815254397302f9c7958dd435', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c6a85a8381e711ea81a800163e045bb0', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('27867f225111d2b83eeaec1699edcda0', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'fc9ca351846111eaab15b4a9fc2c4442', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('28980f46c5b177b84844800356c2008b', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'e549c4b8-72ca-4ba3-91a8-9ffa1daf77cf', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('2f02adb5e1c59e4a8c3ff5e9af02f7ce', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'fc9d3120846111eaab15b4a9fc2c4442', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('2fede6ae1dbcc0d2704e1ae91b41adfc', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'b01614ab-0538-4cca-bb61-b46f18c60aa4', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('3044581c2f62c908230ad1193608558a', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '8cbcf9939f2f11ea9b7bb4a9fc2c4442', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('304fd1001e7905dafbfe1ae1b2aa3a0f', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '1dec779d-a9ec-448a-9389-a2b4eefce119', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('36c7cf640ef587bf690fb1ec25af7334', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f28b9215-3119-482d-bdc1-1f4c3f7c0869', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('3c25cbc6d7c481138dd2739e4fb06837', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('3eba9ff7effd4de33c765cb008438714', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '438641da495235106c2d311703ac4290', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('405946769f35177022272e08544c5ae7', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '0d99b687-3f46-4632-9d56-8dd5e476dae7', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('44d5662eee3c3112dbcf3cc6737c19e2', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '8cbd63619f2f11ea9b7bb4a9fc2c4442', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('45a3d20dca4364236500c4fba9a10b57', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c6a0d80a81e711ea81a800163e045bb0', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('4f6043f9c7185c162d947795915e5e13', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c6a36ae381e711ea81a800163e045bb0', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('5155a37ee7ff91547cd839241ed94fe1', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('51793a9c37b397dfd8e0bc7311e591f8', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'd372f1a482e69165151a7dbab0937903', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('52871004735c49e690d0d711bf4d64ff', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '010bcf8b-3172-4331-9941-25788ca8cbb0', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('59e0cd14166b0b06546f412598bb131f', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'fc9d5be5846111eaab15b4a9fc2c4442', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('5c89472d5d815b88a30e9685168128f9', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c038dc93-f30d-4802-a090-be352eab341a', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('5e6560a5889dd953577a94ff77705566', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '4018e179-e599-41d0-bac5-c5408e1d4bc6', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('62689535e15e0b011275bbb34ca16439', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3c390dfd-0d9a-46de-9a5b-1ed884febcb2', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('62a70113acd9da14cbac455266084e95', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'db2d31b7-fdcb-478e-bfde-a55eb8b0aa43', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('6669dd1e0ddda4b685aa7ded93b78c8c', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '8cbdc0cc9f2f11ea9b7bb4a9fc2c4442', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('72eb62e03749aaaff4ba01f42ab96cd8', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'fc9dc9cd846111eaab15b4a9fc2c4442', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('74a4b07aae4c87db31d2cc163dff349d', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '78f8e29a-cccd-49e5-ada7-5af40dd95312', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('77f34fc87d06c72908ff2279a2de1a08', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '26764d88-1d90-402d-b355-a75deef116f2', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('7f94ff31fc648c6b8731b8b56ac364ae', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '8cbdf07f9f2f11ea9b7bb4a9fc2c4442', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('8a3848c5a0950aac268fd4fd665846eb', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f2ff9320-c643-4c85-8b68-15f86d47b88b', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('8b4902e8e00276d90c46a5d22fb75bba', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c69c2d5c81e711ea81a800163e045bb0', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('8c0345ec3970ea5a9d9226a47c03e1b6', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '37101ed5-e840-4082-ae33-682ca6e41ad8', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('8d148cc4e3bb61c31ef51d4cd9478c6e', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '65bdb02f47b94e71c2a2b9e459bde909', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('91da6c687aaedcf00b4e9891f1239d78', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '88fd59f1aa3911ea883eb4a9fc2c4442', '2020-06-15 15:21:17');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('91da6c687aaedcf00b4e9891f1349d78', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '88fd953aaa3911ea883eb4a9fc2c4442', '2020-06-15 15:21:17');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('91da6c687aaedcf00b4e9891f1769d78', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '88fded61aa3911ea883eb4a9fc2c4442', '2020-06-15 15:21:17');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('91da6c687aaedcf00b4e9891f6529d78', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '88fd1359aa3911ea883eb4a9fc2c4442', '2020-06-15 15:21:17');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('926b83a8dec45591f74f6a46b333dbcf', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '43d1bbf7-fc4a-4a10-9ad1-205b16c7c05f', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('96730aa8817add9dc7e2e9fd25223394', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '09c7e071846211eaab15b4a9fc2c4442', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('9e5f340a3395eb5d9e1b0d0942cd82ff', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'b180aafa-0d1a-4898-b838-bc20cd44356d', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('9f93309e347ad500e6ecfec95049603b', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f21ed5e8-0756-45dc-91c5-f58a9463caaa', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('a000d490222c5b94b9863b06d099a724', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '355f387f-a22b-4f8c-9cd6-ae10e930cd70', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('a1e5096b787c804882af130561aa8a27', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '992d1a8d-b5f8-44fc-9a48-4b3e60a7b15e', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('b40a3d811b75bf9af4605df07a85742b', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '783aedd8-5d93-46b6-8c6d-e4d3f0f3f466', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('b4a080117b6c134d903c22e75472cff5', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '526be97ba24c5a1b3418cdf7ca176b7e', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('bd573951753928eeed05d4ec20d663dd', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3a93a7e3-956a-408e-b2e4-108e9ece8f04', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('be71879ab852e7d57366e6b5bd12dfab', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '1a2ec857-e775-4377-9fb7-e3c77738b3e5', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('c506ace889e2c6b9eaf57d80b51425a2', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '60c3443a-5ec9-4ea7-9484-d2870af93059', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('c664b0cc67b2b2a374fade6e9cf5561e', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'a390845b-a53d-4bc9-af5d-331c37f34e6f', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('caf59e517a868a69f44444fc0ffb51a1', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '8cbe19f09f2f11ea9b7bb4a9fc2c4442', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('d142e16b65bcdb50fbb6b4ccde30fae2', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c30389e8-eb3e-4a0d-99c4-639e1893f50a', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('d4e4cea0a4317f235f8966b181055baf', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '09c7a1ec846211eaab15b4a9fc2c4442', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('d785c055b0557d000bc9c258c5466f1e', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'fc9da4f7846111eaab15b4a9fc2c4442', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('d7960d237bd2014eda33a99c29bccb80', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2073345f-7344-43fe-9084-b7add56da652', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('db395de99411e893a25859c1e4052623', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '9b02bb94c01d69b8dabeaeae1c85bbee', '2020-05-26 14:21:56');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('dcaf7ae345d6393aa6624f16045d6792', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '817a58d1-ec82-4106-870a-bcc0bfaee0e7', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('f40541735a799ac570c23518b03cb2db', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'eef2b95e5d5ae2ebbb9d835e8850fd1f', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('f47cf0a30a02b830cd620226725c031c', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '761db494-833d-4a6c-94b4-3a7409fd9a78', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('fc81d4958fb04591a7bc0cf4b02379c0', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '58612968-d93c-4c21-8fdc-a825c0ab0275', '2020-04-22 15:48:47');

INSERT INTO sys_user(id, username, salt, password, phone, dept_id, real_name, nick_name, email, status, sex, deleted, create_id, update_id, create_where, create_time, update_time) VALUES ('1dfaafa7-fddf-46f2-b3d8-11bfe9ac7230', 'dev123', 'fa386978e2c04b7baef1', '9204993352a150ddd9febc421a5e5636', '131777777777', '4bd0b0a3-097d-4902-a1f7-641ea3b771bd', NULL, NULL, NULL, 1, 1, 1, NULL, 'fcf34b56-a7a2-4719-9236-867495e74c31', 1, '2019-11-09 22:47:30', '2020-03-19 02:39:12');
INSERT INTO sys_user(id, username, salt, password, phone, dept_id, real_name, nick_name, email, status, sex, deleted, create_id, update_id, create_where, create_time, update_time) VALUES ('7f8c0e32-058e-409d-8e7c-22a9afe6a0a0', 'zhangyang', '062f33e33afe4509b24b', '920f5d75077b25912e5054c4d58e0a4c', '13899999999', '4bd0b0a3-097d-4902-a1f7-641ea3b771bd', '张杨', NULL, '16399@163.com', 1, 1, 1, NULL, '7f8c0e32-058e-409d-8e7c-22a9afe6a0a0', 1, '2019-11-09 21:23:36', '2019-11-09 22:45:36');
INSERT INTO sys_user(id, username, salt, password, phone, dept_id, real_name, nick_name, email, status, sex, deleted, create_id, update_id, create_where, create_time, update_time) VALUES ('cd58111d005641a162cb2528c71889ae', 'admin123123', 'db7b27ee460d47a0ad1f', 'f9ac8bba2c66976c73120fcd41a5a79a', '123123123', '4bd0b0a3-097d-4902-a1f7-641ea3b771bd', NULL, NULL, NULL, 1, 1, 1, NULL, NULL, 1, '2020-03-18 09:49:31', NULL);
INSERT INTO sys_user(id, username, salt, password, phone, dept_id, real_name, nick_name, email, status, sex, deleted, create_id, update_id, create_where, create_time, update_time) VALUES ('d860412c-9a4b-404b-8b71-ae8e3f4c27b7', 't', 'a8aed440045b4e0c9c69', 'b08f907d879ea98a681df0082a9cb95e', '13899999999', '72a4f388-50f8-4019-8c67-530cd7c74e7a', NULL, NULL, NULL, 1, 1, 1, NULL, NULL, 1, '2019-11-19 10:34:24', NULL);
INSERT INTO sys_user(id, username, salt, password, phone, dept_id, real_name, nick_name, email, status, sex, deleted, create_id, update_id, create_where, create_time, update_time) VALUES ('fcf34b56-a7a2-4719-9236-867495e74c31', 'admin', '324ce32d86224b00a02b', '2102b59a75ab87616b62d0b9432569d0', '13888888888', '72a4f388-50f8-4019-8c67-530cd7c74e7a', '爱糖宝12', '小霍', 'yingxue@163.com', 1, 2, 1, NULL, 'fcf34b56-a7a2-4719-9236-867495e74c31', 3, '2019-09-22 19:38:05', '2020-03-18 09:15:22');

INSERT INTO sys_user_role(id, user_id, role_id, create_time) VALUES ('2ebca69cc810541c81348d72aa5cab98', '7884efa332130c80be43ef1c61d91f8b', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', '2020-03-19 02:23:13');
INSERT INTO sys_user_role(id, user_id, role_id, create_time) VALUES ('33137e13-2318-42e4-a8e7-4bb7eea0f4ef', '1ff14b2c-d32b-496b-8fb7-d988b838b8e0', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2020-03-18 08:16:12');
INSERT INTO sys_user_role(id, user_id, role_id, create_time) VALUES ('69d5bf3a-37a8-4da1-8941-fe44caa51af0', '7f8c0e32-058e-409d-8e7c-22a9afe6a0a0', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2019-11-09 21:25:49');
INSERT INTO sys_user_role(id, user_id, role_id, create_time) VALUES ('d797bb615b355118d35929ffa573cc86', 'ffd81881f21b48529086233f832f3480', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', '2020-03-19 02:23:02');
INSERT INTO sys_user_role(id, user_id, role_id, create_time) VALUES ('da3dd29ea2f7957c144149e5040ce275', 'fcf34b56-a7a2-4719-9236-867495e74c31', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2020-03-19 12:17:45');
INSERT INTO sys_user_role(id, user_id, role_id, create_time) VALUES ('ea228f70b42849dfffeffdcad04c3a45', 'ee477bad852b0177b3fa10a2243c98d2', '8dd881c7-078c-406a-9c5f-242ab9ecfcb5', '2020-03-19 02:34:47');
INSERT INTO sys_user_role(id, user_id, role_id, create_time) VALUES ('f8ad372b79490bd1ad99e7ac77409647', '1dfaafa7-fddf-46f2-b3d8-11bfe9ac7230', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2020-03-19 02:39:01');
INSERT INTO sys_user_role(id, user_id, role_id, create_time) VALUES ('fd1512dc-bf59-45b2-aa14-93da48ff0e8c', 'd860412c-9a4b-404b-8b71-ae8e3f4c27b7', 'de54c167-e733-4b5b-83dd-ce10edd078f5', '2019-11-19 10:35:14');
