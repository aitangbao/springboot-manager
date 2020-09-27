# springboot-manager

## 介绍
基于SpringBoot + Thymeleaf + Layui + Apache Shiro + Redis + Mybatis Plus 的后台管理系统    
数据库支持 MySQL、Oracle、sqlServer 等主流数据库  
提供代码生成器，基本增删改查无需编写，可快速完成开发任务。  
后台接口RESTful 风格，支持前后端分离，可与app公用一套接口。  
开发最精简，可当脚手架，适合你来 DIY

## 特征
- 后台接口RESTful 风格，支持前后端分离，可与app公用一套接口
- 采用RBAC的权限控制
- 统一响应结果封装及生成工具
- 统一异常处理
- Shiro + Redis 实现 Token 角色权限认证
- 使用Druid Spring Boot Starter 集成Druid数据库连接池与监控
- 集成MyBatis-Plus，实现单表业务零SQL
- 支持多数据源，自由切换，只需方法或类上用 @DS 切换数据源
- 集成国人风格的knife4j，自动生成接口文档
- 提供代码生成器(MySQL、Oracle、sqlServer等主流数据库)，生成从Html到Mapper，爽歪歪  

## 代码仓库
Gitee地址：[https://gitee.com/zwens/springboot-manager](https://gitee.com/zwens/springboot-manager)   
GitHub地址：[https://github.com/aitangbao/springboot-manager](https://github.com/aitangbao/springboot-manager)  
如需更简洁版，请移步:[https://gitee.com/zwens/springboot-manager/tree/simple/](https://gitee.com/zwens/springboot-manager/tree/simple/) 

## 开发文档&项目演示
- 开发文档：[http://doc.aitangbao.com.cn](http://doc.aitangbao.com.cn) 
- 演示地址：[http://manager.aitangbao.com.cn](http://manager.aitangbao.com.cn/login) 
- 账号密码：guest/123456
- 带宽1m 不太给力 请见谅 :joy:

## 开发建议
- Model内成员变量建议与表字段数量对应，如需扩展成员变量（比如连表查询）建议创建VO，否则需在扩展的成员变量上加@TableField(exist = false)
- 建议业务失败直接使用throw new BusinessException("ErrorMessage")抛出，由统一异常处理器来封装业务失败的响应结果，会直接被封装为{"code":500002,"message":"ErrorMessage"}返回，尽情抛出；
- token支持header跟query传参形式，如:
    - ajax中设置header:```beforeSend: function(request) {request.setRequestHeader("authorization", "有效的token");}```
    - query:```?authorization=有效的token ```

## 使用说明
- 使用IDE导入本项目，IDE需要安装lombok插件
- 下载redis 启动redis
- 创建数据库, 导入***.sql
- 配置application-dev.yml中的redis以及数据库连接
- 运行项目
   	1. 直接运行CompanyProjectApplication.java
	2. 项目根目录下执行mvn -X clean package -Dmaven.test.skip=true编译打包，然后执行java -jar manager.jar
- 接口文档访问 http://localhost:8080/doc.html
- 登录地址 http://localhost:8080/index/login 用户名密码:admin/123456
- 代码生成使用  
    1. 逻辑删除字段，请统一用deleted字段: 1未删 0已删; 主键请统一格式: `id` varchar(50) 类型; 列名请勿使用数据库关键字
    2. application.yml中配置： 使用代码生成模块时 指定要生成的表存在于哪种数据库。project.database=mysql  
    3. 点击[代码生成]菜单，生成一个或多个表的代码，下载到本地  
    4. 解压下载的代码，直接复制main文件夹到本地项目的src目录下  
    5. 数据库执行sql，生成菜单
	6. 点击[角色管理]菜单，修改角色所绑定的菜单的权限，刷新页面查看
	
## 技术文档
* 核心框架：[Spring Boot](https://spring.io/projects/spring-boot)
* 前端框架: [Layui](https://www.layui.com/)
* 持久层框架：[MyBatis-Plus](https://mybatis.plus)
* 分页：[Page](https://mybatis.plus/guide/page.html)
* 数据库连接池：[Alibaba Druid](https://github.com/alibaba/druid/)
* 安全框架：[Apache Shiro](http://shiro.apache.org/)
* 缓存框架：[Redis](https://redis.io/)
* 接口文档：[Knife4j](https://doc.xiaominfo.com/)
* 模板引擎：[Thymeleaf](https://www.thymeleaf.org/)
* 阿里巴巴Java开发手册[最新版下载](https://github.com/alibaba/p3c)

## 参与贡献
1. Fork 本项目
2. 新建 feature_xxx 分支
3. 提交代码
4. 提交 Pull Request

## 菜单
- 组织管理
	- 菜单权限管理
	- 角色管理
	- 用户管理
	- 部门管理
- 系统管理
	- 代码生成
	- 字典管理
	- 日志管理
- 其他
	- 表单构建
	- SQL监控
	- 接口管理