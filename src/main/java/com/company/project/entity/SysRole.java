package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.company.project.vo.req.PageReqVO;
import com.company.project.vo.resp.PermissionRespNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class SysRole extends PageReqVO implements Serializable {
    @TableId(type = IdType.UUID)
    private String id;

    @NotBlank(message = "名称不能为空")
    private String name;

    private String description;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    @TableField(exist = false)
    private List<PermissionRespNode> permissionRespNodes;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private List<String> permissions;

}