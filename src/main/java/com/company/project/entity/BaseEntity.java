package com.company.project.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * BaseEntity
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
@JsonIgnoreProperties(value = {"page", "limit", "getQueryPage"})
public class BaseEntity {
    @JSONField(serialize = false)
    @TableField(exist = false)
    private Integer page = 1;

    @JSONField(serialize = false)
    @TableField(exist = false)
    private Integer limit = 10;

    /**
     * 数据权限：用户id
     */
    @TableField(exist = false)
    private List<String> createIds;

    /**
     * page条件
     *
     * @param <T>
     * @return
     */
    @JSONField(serialize = false)
    public <T> Page getQueryPage() {
        return new Page<T>(page, limit);
    }
}
