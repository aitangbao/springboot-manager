package com.company.project.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @ClassName: PermissionAddReqVO
 */
@Data
public class PermissionAddReqVO {
    @ApiModelProperty(value = "菜单权限名称")
    @NotBlank(message = "菜单权限名称不能为空")
    private String name;

    @ApiModelProperty(value = "菜单权限标识，shiro 适配restful")
    private String perms;

    @ApiModelProperty(value = "接口地址")
    private String url;

    @ApiModelProperty(value = "请求方式 和url 配合使用 (我们用 路径匹配的方式做权限管理的时候用到)")
    private String method;

    @ApiModelProperty(value = "父级id")
    @NotNull(message = "所属菜单不能为空")
    private String pid;

    @ApiModelProperty(value = "排序码")
    private Integer orderNum;

    @ApiModelProperty(value = "菜单权限类型(1:目录;2:菜单;3:按钮)")
    @NotNull(message = "菜单权限类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "状态1:正常 0：禁用")
    private Integer status;

    @ApiModelProperty(value = "编码(前后端分离 前段对按钮显示隐藏控制 btn-permission-search 代表 菜单权限管理的列表查询按钮)")
    private String code;

}
