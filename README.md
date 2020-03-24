# springboot-manager

## 介绍
基于SpringBoot 、Apache Shiro、Redis、Mybatis Plus 、Thymeleaf、Layui 后台管理系统  
提供代码生成器，基本增删改查无需编写，可快速完成开发任务。开发最精简，可当脚手架，适合你来diy

## 特征&提供
- 后台接口RESTful 风格，支持前后端分离，可与app公用一套接口。
- 采用RBAC的权限控制
- 统一响应结果封装及生成工具
- 统一异常处理
- Shiro + Redis 实现 Token 角色权限认证
- 使用Druid Spring Boot Starter 集成Druid数据库连接池与监控
- 集成MyBatis-Plus，实现单表业务零SQL
- 支持多数据源，自由切换，只需方法或类上用 @DS 切换数据源
- 集成国人风格的knife4j，自动生成接口文档
- 提供代码生成器，生成从Html到Mapper，自动赋给admin超级权限，爽歪歪

## 技术
* 核心框架：spring boot 2.1.6
* 持久层框架：mybatis plus
* 数据库连接池：alibaba druid
* 安全框架：apache shiro
* 缓存框架：redis
* 日志框架：logback
* 接口文档：Knife4j
* 前端模板：thymeleaf+layui2x

## 开发建议
- Model内成员变量建议与表字段数量对应，如需扩展成员变量（比如连表查询）建议创建VO，否则需在扩展的成员变量上加@TableField(exist = false)
- 如果表有是否删除字段，需要在Model注解@TableLogic 默认1未删 0删除， 或@TableLogic(value="逻辑未删除值",delval="逻辑删除值")
- 后台登陆账户密码 admin/123456

#### 参与贡献
1. Fork 本项目
2. 新建 feature_xxx 分支
3. 提交代码
4. 提交 Pull Request


## **效果图**

![image-20200319081918950](http://tuchuang.aitangbao.com.cn/image-20200319081918950.png)

![image-20200318173023759](http://tuchuang.aitangbao.com.cn/image-20200318173023759.png)

![image-20200324074848438](http://tuchuang.aitangbao.com.cn/image-20200324074848438.png)

![image-20200318173110441](http://tuchuang.aitangbao.com.cn/image-20200318173110441.png)