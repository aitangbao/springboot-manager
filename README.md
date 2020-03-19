# springboot-manager

#### 介绍
基于spring boot 、shiro、redis、Knife4j、mybatis plus 、thymeleaf、layui 后台管理系统


## 特征&提供
- 统一响应结果封装及生成工具
- 统一异常处理
- 采用redis token认证，支持单登陆端/多登陆端登陆
- 使用Druid Spring Boot Starter 集成Druid数据库连接池与监控
- 集成MyBatis-Plus，实现单表业务零SQL
- 支持多数据源，自由切换，只需方法或类上用 @DS 切换数据源
- 集成国人风格的knife4j，自动生成接口文档
- 提供代码生成器，生成controller,service,serviceImpl,dao,mapper.xml

#### **效果图**

![image-20200319081918950](http://tuchuang.aitangbao.com.cn/image-20200319081918950.png)

![image-20200318173023759](http://tuchuang.aitangbao.com.cn/image-20200318173023759.png)

![image-20200318173110441](http://tuchuang.aitangbao.com.cn/image-20200318173110441.png)



## 相关文档
- Spring Boot（[springboot官方](https://spring.io/projects/spring-boot/)）
- MyBatis-Plus ([查看官方中文文档](https://mp.baomidou.com/guide/))
- MyBatis-Plus分页插件（[查看官方中文文档](https://mp.baomidou.com/guide/page.html)）
- Druid Spring Boot Starter（[查看官方中文文档](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter/)）
- Fastjson（[查看官方中文文档](https://github.com/Alibaba/fastjson/wiki/%E9%A6%96%E9%A1%B5)）
- 阿里巴巴Java开发手册[最新版下载](https://github.com/alibaba/p3c)
其他

#### **部署**

- 下载redis 启动redis
- 创建company_project数据库
- 导入company_project.sql
- 启动项目
- 接口文档访问 http://localhost:8080/doc.html
- 登录地址 http://localhost:8080/index/login
- 登录密码 admin/123456


