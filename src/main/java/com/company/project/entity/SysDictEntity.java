package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.company.project.vo.req.PageReqVO;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据字典表
 *
 * @author manager
 * @email *****@mail.com
 * @date 2020-04-19 10:44:04
 */
@Data
@TableName("sys_dict")
public class SysDictEntity extends PageReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId("id")
	private String id;

	/**
	 * 字典名称
	 */
	@TableField("name")
	private String name;

	/**
	 * 备注
	 */
	@TableField("remark")
	private String remark;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;



}
