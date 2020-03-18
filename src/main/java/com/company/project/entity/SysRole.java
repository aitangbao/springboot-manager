package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.company.project.vo.resp.PermissionRespNode;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysRole implements Serializable {
    private String id;

    private String name;

    private String description;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    @TableField(exist = false)
    private List<PermissionRespNode> permissionRespNodes;


}