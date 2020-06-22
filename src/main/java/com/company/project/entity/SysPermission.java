package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class SysPermission implements Serializable {


    @TableId(type = IdType.UUID)
    private String id;

    @NotBlank(message = "菜单权限名称不能为空")
    private String name;

    private String perms;

    private String url;

    private String icon;

    private String target;

    @NotNull(message = "所属菜单不能为空")
    private String pid;

    private Integer orderNum;

    @NotNull(message = "菜单权限类型不能为空")
    private Integer type;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    @TableField(exist = false)
    private String pidName;
    
}