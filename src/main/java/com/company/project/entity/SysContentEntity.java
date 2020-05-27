package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.company.project.vo.req.PageReqVO;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 *
 * @author manager
 * @email *****@mail.com
 * @date 2020-05-26 17:00:59
 */
@Data
@TableName("sys_content")
public class SysContentEntity extends PageReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId("id")
	private String id;

	/**
	 * 
	 */
	@TableField("title")
	private String title;

	/**
	 * 
	 */
	@TableField("content")
	private String content;

	/**
	 * 
	 */
	@TableField("create_time")
	private Date createTime;


}
