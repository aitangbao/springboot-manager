# aitangbao

#### 介绍
基于spring boot 2.1.6、shiro、redis、Knife4j、mybatis plus 、thymeleaf、layui 后台管理系统， 后台接口RESTful 风格，支持前后端分离，app公用一套接口。 

#### 软件架构
软件架构说明
* 核心框架：spring boot 2.1.6
* 持久层框架：mybatis plus
* 数据库连接池：alibaba druid
* 安全框架：apache shiro
* 缓存框架：redis
* 日志框架：logback
* 接口文档：Knife4j
* 前端模板：thymeleaf+layui2x

#### **部署**

- 下载redis 启动redis
- 创建company_project数据库
- 导入company_project.sql
- 启动项目
- 接口文档访问 http://localhost:8080/doc.html
- 登录地址 http://localhost:8080/index/login
- 登录密码 admin/123456


#### **效果图**



![image-20200318173023759](http://tuchuang.aitangbao.com.cn/image-20200318173023759.png)

![image-20200318173110441](http://tuchuang.aitangbao.com.cn/image-20200318173110441.png)