package com.company.project.vo.req;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * PageReqVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class PageReqVO {
    @ApiModelProperty(value = "第几页")
    @TableField(exist = false)
    private int page=1;

    @ApiModelProperty(value = "分页数量")
    @TableField(exist = false)
    private int limit=10;
}
