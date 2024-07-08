# springboot-manager

## 介绍
基于SpringBoot + Mybatis Plus + SaToken + Thymeleaf + Layui的后台管理系统  
接入Sa-Token，支持菜单权限
数据库支持 MySQL、Oracle、sqlServer 等主流数据库  
提供代码生成器，基本增删改查无需编写，可快速完成开发任务。  
后台接口RESTful 风格，支持前后端分离，可与app公用一套接口。  
开发最精简，可当脚手架，适合你来 DIY  
**如果喜欢，防止您迷路， 请尽快Star项目。多了您的支持，更多了一份动力！感谢~**

## 特征
- 后台接口RESTful 风格，支持前后端分离，可与app公用一套接口
- 采用RBAC的权限控制
- 统一响应结果封装及生成工具 统一异常处理
- 拥抱Sa-Token 实现角色权限认证，让鉴权变得简单、优雅！
- 使用Druid Spring Boot Starter 集成Druid数据库连接池与监控
- 集成MyBatis-Plus，实现单表业务零SQL
- 支持多数据源，自由切换，只需方法或类上用 @DS 切换数据源
- 集成国人风格的knife4j，自动生成接口文档
- 提供代码生成器(MySQL、Oracle、sqlServer等主流数据库)，生成从Html到Mapper，爽歪歪  

## 代码仓库 最新请移步gitee， github定期同步
Gitee地址：[https://gitee.com/zwens/springboot-manager](https://gitee.com/zwens/springboot-manager)   
GitHub地址：[https://github.com/aitangbao/springboot-manager](https://github.com/aitangbao/springboot-manager)  


## 开发文档&项目演示
- 开发文档：[开发文档wiki](https://gitee.com/zwens/springboot-manager/wikis/pages)
- 演示地址：[springboot-manager](http://1.94.23.145:9000/manager/login)
-  **账号密码：guest/123456** 


## 代码结构
```
├─main
│  ├─java
│  │  └─com
│  │      └─company
│  │          └─project
│  │              ├─CompanyProjectApplication.java 项目启动类
│  │              ├─common      公共资源，如注解、切面、全局异常处理、组件集成、通用工具类等
│  │              ├─controller  Controler层
│  │              ├─entity      实体类
│  │              ├─mapper      DAO层
│  │              ├─service     Service层
│  │              │  └─impl     Service层实现
│  └─resources
│      ├── application-dev.yml  开发环境配置文件
│      ├── application-test.yml 测试环境配置文件
│      ├── application-prod.yml 生产环境配置文件
│      ├── application.yml      通用配置文件
│      ├── logback-spring.xml   日志配置文件
│      ├─mapper                 Mybatis XML文件
│      ├─static                 静态文件
│      │  ├─css                 通用css文件
│      │  ├─images              静态图片
│      │  ├─js                  通用js文件
│      │  ├─layui               layui库
│      │  └─layui-ext           layui插件库
│      ├─template               代码生成模版
│      └─templates              项目页面目录
│          ├─depts              部门管理
│          ├─error              错误页面
│          ├─generator          代码生成管理
│          ├─logs               日志管理
│          ├─menus              菜单管理
│          ├─roles              角色管理
│          ├─syscontent         内容管理
│          ├─sysdict            字典管理
│          ├─sysfiles           文件管理
│          └─users              用户管理
└─test
    └─java
        └─com
            └─company
                └─project
                    ├── CompanyFrameApplicationTests.java 单元测试
```

## 开发建议
- Model内成员变量建议与表字段数量对应，如需扩展成员变量（比如连表查询）建议创建VO，否则需在扩展的成员变量上加@TableField(exist = false)
- 建议业务失败直接使用throw new BusinessException("ErrorMessage")抛出，由统一异常处理器来封装业务失败的响应结果，会直接被封装为{"code":500002,"message":"ErrorMessage"}返回，尽情抛出；
- 数据库基础字段：id(bigint)、remark(varchar)、unable_flag(tinyint)、deleted(tinyint)、create_id(bigint)、update_id(bigint)、create_time(datetime)、update_time(datetime)

## 使用说明
- 使用IDE导入本项目，IDE需要安装lombok插件
- 创建数据库, 如mysql数据库导入mysql.sql
- 配置application-dev.yml中的数据库连接
- 运行项目
   	1. 直接运行CompanyProjectApplication.java
	2. 项目根目录下执行mvn -X clean package -Dmaven.test.skip=true编译打包，然后执行java -jar manager.jar
- 登录地址 http://localhost:8080/manager/index/login 用户名密码:admin/123456
- 代码生成使用  
    1. 逻辑删除字段，请统一用deleted字段: 1未删 0已删; 主键请统一格式: `id` varchar(50) 类型; 列名请勿使用数据库关键字
    2. application.yml中配置： 使用代码生成模块时 指定要生成的表存在于哪种数据库。project.database=mysql  
    3. 点击[代码生成]菜单，生成一个或多个表的代码，下载到本地  
    4. 解压下载的代码，直接复制main文件夹到本地项目的src目录下  
    5. 数据库执行sql，生成菜单
	6. admin 刷新页面即刻查看

## 技术文档
* 核心框架：[Spring Boot](https://spring.io/projects/spring-boot)
* 持久层框架：[MyBatis-Plus](https://mybatis.plus)
* 权限认证：[Sa-Token](https://sa-token.cc/doc.html#/)
* 前端框架: [Layui](https://layui.dev/docs/2/)
* 数据库连接池：[Alibaba Druid](https://github.com/alibaba/druid/)
* 模板引擎：[Thymeleaf](https://www.thymeleaf.org/)
* 阿里巴巴Java开发手册[最新版下载](https://github.com/alibaba/p3c)

## 参与贡献
1. Fork 本项目
2. 新建 feature_xxx 分支
3. 提交代码
4. 提交 Pull Request
	  
## **效果图**

![](https://gitee.com/aitangbao/tuchuang/raw/master/springboot-manager/login.png)

![](https://gitee.com/aitangbao/tuchuang/raw/master/springboot-manager/caidan.png)


### 捐赠
> 项目的发展离不开您的支持， 如果您够宽裕，请作者喝杯咖啡吧！  

![](https://gitee.com/aitangbao/tuchuang/raw/master/springboot-manager/dashang.png)

### 交流群
> 如果大家有疑难杂症，技术交流， 可以加我拉你们进群, 务必备注: 开源
![](https://gitee.com/aitangbao/tuchuang/raw/master/springboot-manager/weixin.jpg)

