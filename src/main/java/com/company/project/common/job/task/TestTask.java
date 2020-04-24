/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.company.project.common.job.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称， 方法名称必须是run
 *
 */
@Component("testTask")
public class TestTask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public void test(String params){
		logger.debug("TestTask定时任务正在执行，参数为：{}", params);
	}
}
