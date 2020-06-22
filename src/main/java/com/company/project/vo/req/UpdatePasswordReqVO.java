package com.company.project.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * UpdatePasswordReqVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class UpdatePasswordReqVO {
    @ApiModelProperty(value = "旧密码")
    private String oldPwd;
    @ApiModelProperty(value = "新密码")
    private String newPwd;
}
