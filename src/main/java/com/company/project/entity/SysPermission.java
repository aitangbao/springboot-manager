package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysPermission implements Serializable {


    @TableId(type = IdType.UUID)
    private String id;

    @NotBlank(message = "菜单权限名称不能为空")
    private String name;

    private String perms;

    private String url;

    @NotNull(message = "所属菜单不能为空")
    private String pid;

    private Integer orderNum;

    @NotNull(message = "菜单权限类型不能为空")
    private Integer type;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private String pidName;
    
}