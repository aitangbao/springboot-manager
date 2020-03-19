package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.utils.DataResult;
import com.company.project.vo.req.PageReqVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import java.util.List;

import javax.annotation.Resource;
<#if restControllerStyle>
import org.springframework.stereotype.Controller;
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
@Controller
<#else>
@Controller
</#if>
@RequestMapping("/")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??>:${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>public class ${table.controllerName} extends ${superControllerClass}{
<#else>public class ${table.controllerName} {
</#if>

    @Resource
    private ${table.serviceName} ${(table.serviceName?substring(1))?uncap_first};

    /**
    * 跳转到页面
    */
    @GetMapping("/index/${entity?uncap_first}")
    public String ${entity?uncap_first}() {
    return "/${entity?uncap_first}/list";
    }

    @ApiOperation(value = "新增${table.comment!}")
    @PostMapping("${entity?uncap_first}/add")
    @RequiresPermissions("${entity?uncap_first}:add")
    @ResponseBody
    public DataResult add(@RequestBody ${entity} ${entity?uncap_first}){
        ${(table.serviceName?substring(1))?uncap_first}.save(${entity?uncap_first});
        return DataResult.success();
    }

    @ApiOperation(value = "删除${table.comment!}")
    @DeleteMapping("${entity?uncap_first}/delete")
    @RequiresPermissions("${entity?uncap_first}:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids){
        ${(table.serviceName?substring(1))?uncap_first}.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新${table.comment!}")
    @PutMapping("${entity?uncap_first}/update")
    @RequiresPermissions("${entity?uncap_first}:update")
    @ResponseBody
    public DataResult update(@RequestBody ${entity} ${entity?uncap_first}){
        ${(table.serviceName?substring(1))?uncap_first}.updateById(${entity?uncap_first});
        return DataResult.success();
    }

    @ApiOperation(value = "查询${table.comment!}分页数据")
    @PostMapping("${entity?uncap_first}/listByPage")
    @RequiresPermissions("${entity?uncap_first}:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody PageReqVO vo){
        Page page = new Page(vo.getPageNum(), vo.getPageSize());
        IPage<${entity}> iPage = ${(table.serviceName?substring(1))?uncap_first}.page(page);
        return DataResult.success(iPage);
    }

}
</#if>