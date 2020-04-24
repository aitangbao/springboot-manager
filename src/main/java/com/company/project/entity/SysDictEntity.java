package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
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
	 * 字典类型
	 */
	@TableField("type")
	private String type;

	/**
	 * 字典码
	 */
	@TableField("code")
	private String code;

	/**
	 * 字典值
	 */
	@TableField("value")
	private String value;

	/**
	 * 排序
	 */
	@TableField("order_num")
	private Integer orderNum;

	/**
	 * 备注
	 */
	@TableField("remark")
	private String remark;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;

	/**
	 * 删除标记 1未删除；0已删除
	 */
	@TableLogic
	private Integer deleted;


}
