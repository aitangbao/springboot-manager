package com.company.project.common.job.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务(演示Demo，可删除)
 * testTask为spring bean的名称， 方法名称必须是run
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Component("testTask")
public class TestTask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public void run(String params){
		logger.debug("TestTask定时任务正在执行，参数为：{}", params);
	}
}
