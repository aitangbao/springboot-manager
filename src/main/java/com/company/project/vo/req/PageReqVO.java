package com.company.project.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: DeptPageReqVO
 */
@Data
public class PageReqVO {
    @ApiModelProperty(value = "第几页")
    private int page=1;

    @ApiModelProperty(value = "分页数量")
    private int limit=10;
}
