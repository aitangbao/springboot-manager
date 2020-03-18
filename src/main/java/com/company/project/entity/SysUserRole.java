package com.company.project.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserRole implements Serializable {
    private String id;

    private String userId;

    private String roleId;

    private Date createTime;


}