package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.company.project.vo.req.PageReqVO;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 定时任务日志
 *
 * @author manager
 * @email *****@mail.com
 * @date 2020-04-22 14:23:35
 */
@Data
@TableName("sys_job_log")
public class SysJobLogEntity extends PageReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务日志id
	 */
	@TableId("id")
	private String id;

	/**
	 * 任务id
	 */
	@TableField("job_id")
	private String jobId;

	/**
	 * spring bean名称
	 */
	@TableField("bean_name")
	private String beanName;

	/**
	 * 参数
	 */
	@TableField("params")
	private String params;

	/**
	 * 任务状态    0：成功    1：失败
	 */
	@TableField("status")
	private Integer status;

	/**
	 * 失败信息
	 */
	@TableField("error")
	private String error;

	/**
	 * 耗时(单位：毫秒)
	 */
	@TableField("times")
	private Integer times;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;


}
