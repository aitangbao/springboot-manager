package com.company.project.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: LoginReqVO
 */
@Data
public class LoginReqVO {
    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String username;
    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
