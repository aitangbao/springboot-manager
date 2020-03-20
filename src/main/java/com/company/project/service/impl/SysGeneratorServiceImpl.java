package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.company.project.entity.SysGenerator;
import com.company.project.entity.SysPermission;
import com.company.project.entity.SysRolePermission;
import com.company.project.exception.BusinessException;
import com.company.project.exception.code.BaseResponseCode;
import com.company.project.mapper.SysGeneratorMapper;
import com.company.project.service.ISysGeneratorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.service.PermissionService;
import com.company.project.service.RolePermissionService;
import com.company.project.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aitangbao
 * @since 2020-03-20
 */
@Service
@Slf4j
public class SysGeneratorServiceImpl extends ServiceImpl<SysGeneratorMapper, SysGenerator> implements ISysGeneratorService {

    @Autowired
    private SysGeneratorMapper sysGeneratorMapper;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private Environment environment;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //逻辑删除字段名, 假如表没有逻辑删除字段，请忽视
    static String logicDeleteFieldName = "deleted";

    @Override
    public IPage<SysGenerator> selectAllTables(Page page) {
        return sysGeneratorMapper.selectAllTables(page);
    }

    @Override
    public void gen(SysGenerator sysGenerator) throws InterruptedException {

        String tableName = sysGenerator.getTableName();
        String pid = sysGenerator.getPid();
        String menuName = sysGenerator.getMenuName();

        if (!this.validateTableNameExist(tableName)) {
            log.info("表不存在， 生成失败");
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO.getCode(), "表不存在， 生成失败");
        }

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("aitangbao");
        gc.setOpen(false);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setDateType(DateType.ONLY_DATE);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(environment.getProperty("spring.datasource.dynamic.datasource.master.url"));
        // dsc.setSchemaName("public");
        dsc.setDriverName(environment.getProperty("spring.datasource.dynamic.datasource.master.driver-class-name"));
        dsc.setUsername(environment.getProperty("spring.datasource.dynamic.datasource.master.username"));
        dsc.setPassword(environment.getProperty("spring.datasource.dynamic.datasource.master.password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.company.project");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        // 如果模板引擎是 velocity
         String templatePath = "/templates/mapper.xml.ftl";
        final String[] entityPath = {""};

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                entityPath[0] = tableInfo.getEntityPath();
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("templates/gen/html/list.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/templates/" + tableInfo.getEntityPath() + "/list.html";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setController("templates/gen/controller.java");
//        templateConfig.setService("templates/service.java");
//        templateConfig.setServiceImpl("templates/serviceImpl.java");
//        templateConfig.setServiceImpl("templates/serviceImpl.java");
//
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(tableName.split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setLogicDeleteFieldName(logicDeleteFieldName); // 逻辑删除字段名称
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
        //实体类名
        String entityName = entityPath[0];
        //生成菜单，绑定admin权限
        genMenuAndBindPermission(pid, menuName, entityName);

        sysGeneratorMapper.insert(sysGenerator);
    }


    //生成菜单，绑定权限
    private void genMenuAndBindPermission(String pid, String menuName, String entityName) throws InterruptedException {
        //菜单插入
        SysPermission sysPermission = new SysPermission();
        String menuId = UUID.randomUUID().toString();
        sysPermission.setId(menuId);
        sysPermission.setName(menuName);
        sysPermission.setPid(pid);
        sysPermission.setUrl("/index/" + entityName);
        sysPermission.setMethod("GET");
        sysPermission.setOrderNum(1);
        sysPermission.setType(2);
        permissionService.save(sysPermission);
        SysRolePermission sysRolePermission = new SysRolePermission();
        sysRolePermission.setPermissionId(menuId);
        sysRolePermission.setRoleId(Constant.SUPER_ROLE_ID);
        rolePermissionService.save(sysRolePermission);

        sysPermission.setId(UUID.randomUUID().toString());
        sysPermission.setPerms(entityName + ":list");
        sysPermission.setPid(menuId);
        sysPermission.setName("列表");
        sysPermission.setUrl(entityName + "/listByPage");
        sysPermission.setMethod("POST");
        sysPermission.setType(3);
        permissionService.save(sysPermission);
        sysRolePermission = new SysRolePermission();
        sysRolePermission.setPermissionId(sysPermission.getId());
        sysRolePermission.setRoleId(Constant.SUPER_ROLE_ID);
        rolePermissionService.save(sysRolePermission);

        sysPermission.setId(UUID.randomUUID().toString());
        sysPermission.setPerms(entityName + ":add");
        sysPermission.setName("添加");
        sysPermission.setUrl(entityName + "/add");
        sysPermission.setMethod("POST");
        permissionService.save(sysPermission);
        sysRolePermission = new SysRolePermission();
        sysRolePermission.setPermissionId(sysPermission.getId());
        sysRolePermission.setRoleId(Constant.SUPER_ROLE_ID);
        rolePermissionService.save(sysRolePermission);

        sysPermission.setId(UUID.randomUUID().toString());
        sysPermission.setPerms(entityName + ":update");
        sysPermission.setName("修改");
        sysPermission.setUrl(entityName + "/update");
        sysPermission.setMethod("PUT");
        permissionService.save(sysPermission);
        sysRolePermission = new SysRolePermission();
        sysRolePermission.setPermissionId(sysPermission.getId());
        sysRolePermission.setRoleId(Constant.SUPER_ROLE_ID);
        rolePermissionService.save(sysRolePermission);

        sysPermission.setId(UUID.randomUUID().toString());
        sysPermission.setPerms(entityName + ":delete");
        sysPermission.setName("删除");
        sysPermission.setUrl(entityName + "/delete");
        sysPermission.setMethod("DELETE");
        permissionService.save(sysPermission);
        sysRolePermission = new SysRolePermission();
        sysRolePermission.setPermissionId(sysPermission.getId());
        sysRolePermission.setRoleId(Constant.SUPER_ROLE_ID);
        rolePermissionService.save(sysRolePermission);

        log.info("生成成功， 请重启项目，登陆admin查看");
    }

    //校验table是否存在
    public boolean validateTableNameExist(String tableName) {
        try {
            jdbcTemplate.queryForObject("select count(*) from " + tableName, Integer.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
