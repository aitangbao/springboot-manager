package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysPermission implements Serializable {
    private String id;

    private String code;

    private String name;

    private String perms;

    private String url;

    private String method;

    private String pid;

    @TableField(exist = false)
    private String pidName;

    private Integer orderNum;

    private Integer type;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer deleted;

}