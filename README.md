# springboot-manager

## 介绍
基于spring boot 、shiro、redis、Knife4j、mybatis plus 、thymeleaf、layui 后台管理系统


## 特征&提供
- 后台接口RESTful 风格，支持前后端分离，可与app公用一套接口。
- 统一响应结果封装及生成工具
- 统一异常处理
- 采用RBAC的权限控制
- 采用shiro redis token 角色权限管理认证
- 使用Druid Spring Boot Starter 集成Druid数据库连接池与监控
- 集成MyBatis-Plus，实现单表业务零SQL
- 支持多数据源，自由切换，只需方法或类上用 @DS 切换数据源
- 集成国人风格的knife4j，自动生成接口文档
- 提供代码生成器，生成controller,service,serviceImpl,dao,mapper.xml（待完善）

## 技术
* 核心框架：spring boot 2.1.6
* 持久层框架：mybatis plus
* 数据库连接池：alibaba druid
* 安全框架：apache shiro
* 缓存框架：redis
* 日志框架：logback
* 接口文档：Knife4j
* 前端模板：thymeleaf+layui2x

## 系统功能


## **部署**

- 下载redis 启动redis
- 创建company_project数据库
- 导入company_project.sql
- 启动项目
- 接口文档访问 http://localhost:8080/doc.html
- 登录地址 http://localhost:8080/index/login
- 登录密码 admin/123456


## **效果图**

![image-20200319081918950](http://tuchuang.aitangbao.com.cn/image-20200319081918950.png)

![image-20200318173023759](http://tuchuang.aitangbao.com.cn/image-20200318173023759.png)

![image-20200318173110441](http://tuchuang.aitangbao.com.cn/image-20200318173110441.png)

