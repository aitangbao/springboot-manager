package com.company.project.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: PermissionRespNode
 */
@Data
public class PermissionRespNode {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "菜单权限名称")
    private String title;

    @ApiModelProperty(value = "菜单权限标识，shiro 适配restful")
    private String perms;

    @ApiModelProperty(value = "接口地址")
    private String url;

    @ApiModelProperty(value = "请求方式 和url 配合使用 (我们用 路径匹配的方式做权限管理的时候用到)")
    private String method;

    @ApiModelProperty(value = "父级id")
    private String pid;

    @ApiModelProperty(value = "父级名称")
    private String pidName;

    @ApiModelProperty(value = "菜单权限类型(1:目录;2:菜单;3:按钮)")
    private Integer type;

    @ApiModelProperty(value = "编码(前后端分离 前段对按钮显示隐藏控制 btn-permission-search 代表 菜单权限管理的列表查询按钮)")
    private String code;

    @ApiModelProperty(value = "排序码")
    private Integer orderNum;

    @ApiModelProperty(value = "是否展开 默认不展开(false)")
    private boolean spread=true;

    @ApiModelProperty(value = "是否选中 默认false")
    private boolean checked;
    private List<?> children;


}
