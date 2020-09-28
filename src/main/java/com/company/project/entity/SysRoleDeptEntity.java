package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.company.project.entity.BaseEntity;


import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 角色部门
 *
 * @author wenbin
 * @email *****@mail.com
 * @date 2020-09-27 17:30:15
 */
@Data
@TableName("sys_role_dept")
public class SysRoleDeptEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId("id")
	private String id;

	/**
	 * 角色id
	 */
	@TableField("role_id")
	private String roleId;

	/**
	 * 菜单权限id
	 */
	@TableField("dept_id")
	private String deptId;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;


}
