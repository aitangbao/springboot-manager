package com.company.project.vo.req;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class PageReqVO {
    @ApiModelProperty(value = "第几页")
    @TableField(exist = false)
    private int page=1;

    @ApiModelProperty(value = "分页数量")
    @TableField(exist = false)
    private int limit=10;
}
