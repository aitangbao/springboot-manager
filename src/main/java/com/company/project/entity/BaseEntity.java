package com.company.project.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * BaseEntity
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class BaseEntity {
    @JSONField(serialize=false)
    @TableField(exist = false)
    private int page = 1;

    @JSONField(serialize=false)
    @TableField(exist = false)
    private int limit = 10;
}
