package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysRolePermission implements Serializable {
    @TableId(type = IdType.UUID)
    private String id;

    private String roleId;

    private String permissionId;

    private Date createTime;

}