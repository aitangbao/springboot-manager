package com.company.project.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @ClassName: UserUpdateReqVO
 */
@Data
public class UserUpdateReqVO {
    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "所属机构")
    private String deptId;

    @ApiModelProperty(value = "真实名称")
    private String realName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "账户状态(1.正常 2.锁定 )")
    private Integer status;

    @ApiModelProperty(value = "性别(1.男 2.女)")
    private Integer sex;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "所拥有的角色")
    private List<String> roleIds;

}
