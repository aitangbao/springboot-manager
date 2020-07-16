package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.company.project.vo.req.PageReqVO;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典明细
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dict_detail")
public class SysDictDetailEntity extends PageReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId("id")
	private String id;

	/**
	 * 字典标签
	 */
	@TableField("label")
	private String label;

	/**
	 * 字典值
	 */
	@TableField("value")
	private String value;

	/**
	 * 排序
	 */
	@TableField("sort")
	private Integer sort;

	/**
	 * 字典id
	 */
	@TableField("dict_id")
	private String dictId;

	/**
	 * 创建日期
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 字典name
	 */
	@TableField(exist = false)
	private String dictName;

}
