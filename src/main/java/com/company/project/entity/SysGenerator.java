package com.company.project.entity;

import com.company.project.vo.req.PageReqVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 代码生成
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysGenerator extends PageReqVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tableName;

    private Date createTime;

    private String tableComment;

}
