package com.company.project.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: DeptUpdateReqVO
 * 更新 组织
 */
@Data
public class DeptUpdateReqVO {
    @ApiModelProperty(value = "机构id")
    @NotBlank(message = "机构id不能为空")
    private String id;
    @ApiModelProperty(value = "机构名称")
    private String name;
    @ApiModelProperty(value = "父级id")
    private String pid;
    @ApiModelProperty(value = "机构状态(1:正常；0:弃用)")
    private Integer status;

    @ApiModelProperty(value = "部门经理id")
    private String deptManagerId;

    @ApiModelProperty(value = "部门经理名称")
    private String managerName;

    @ApiModelProperty(value = "部门经理电话")
    private String phone;
}
