package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUser implements Serializable {
    @TableId(type = IdType.UUID)
    private String id;

    private String username;

    private String salt;

    private String password;

    private String phone;

    private String deptId;

    @TableField(exist = false)
    private String deptName;

    private String realName;

    private String nickName;

    private String email;

    private Integer status;

    private Integer sex;

    @TableLogic
    private Integer deleted;

    private String createId;

    private String updateId;

    private Integer createWhere;

    private Date createTime;

    private Date updateTime;
}