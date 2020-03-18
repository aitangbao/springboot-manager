package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.utils.DataResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.*;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@Api(tags = {"${table.comment!}"})
@Slf4j
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??>:${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>public class ${table.controllerName} extends ${superControllerClass}{
<#else>public class ${table.controllerName} {
</#if>

    @Resource
    private ${table.serviceName} ${(table.serviceName?substring(1))?uncap_first};


    @ApiOperation(value = "新增${table.comment!}")
    @PostMapping("add")
    public DataResult add(@RequestBody ${entity} ${entity?uncap_first}){
        ${(table.serviceName?substring(1))?uncap_first}.save(${entity?uncap_first});
        return DataResult.success();
    }

    @ApiOperation(value = "删除${table.comment!}")
    @PostMapping("delete/{id}")
    public DataResult delete(@PathVariable("id") Long id){
        ${(table.serviceName?substring(1))?uncap_first}.removeById(id);
        return DataResult.success();
    }

    @ApiOperation(value = "更新${table.comment!}")
    @PostMapping("update")
    public DataResult update(@RequestBody ${entity} ${entity?uncap_first}){
        ${(table.serviceName?substring(1))?uncap_first}.updateById(${entity?uncap_first});
        return DataResult.success();
    }

    @ApiOperation(value = "查询${table.comment!}分页数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "页码"),
        @ApiImplicitParam(name = "pageCount", value = "每页条数")
    })
    @GetMapping("listByPage")
    public DataResult findListByPage(@RequestParam Integer currentPage,
                                   @RequestParam Integer pageCount){
        Page page = new Page(currentPage, pageCount);
        IPage<${entity}> iPage = ${(table.serviceName?substring(1))?uncap_first}.page(page);
        return DataResult.success(iPage);
    }

    @ApiOperation(value = "id查询${table.comment!}")
    @GetMapping("getById/{id}")
    public DataResult findById(@PathVariable Long id){
        return DataResult.success(${(table.serviceName?substring(1))?uncap_first}.getById(id));
    }

}
</#if>