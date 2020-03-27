package com.company.project.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.company.project.vo.req.PageReqVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author aitangbao
 * @since 2020-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysGenerator extends PageReqVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 上级菜单
     */
    private String pid;

    private Date genTime;

    @TableField(exist = false)
    private Date createTime;

    @TableField(exist = false)
    private String tableComment;

}
