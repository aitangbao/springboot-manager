package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.company.project.vo.req.PageReqVO;
import com.company.project.vo.resp.PermissionRespNode;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysRole extends PageReqVO implements Serializable {
    @TableId(type = IdType.UUID)
    private String id;

    private String name;

    private String description;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private List<PermissionRespNode> permissionRespNodes;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;


}