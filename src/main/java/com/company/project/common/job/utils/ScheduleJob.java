package com.company.project.common.job.utils;

import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.SpringContextUtils;
import com.company.project.entity.SysJobEntity;
import com.company.project.entity.SysJobLogEntity;
import com.company.project.service.SysJobLogService;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;


/**
 * 定时任务
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public class ScheduleJob extends QuartzJobBean {
	private Logger logger = LoggerFactory.getLogger(getClass());

	final SysJobLogService sysJobLogService;

	public ScheduleJob(SysJobLogService sysJobLogService) {
		this.sysJobLogService = sysJobLogService;
	}


	@Override
    protected void executeInternal(JobExecutionContext context){
        SysJobEntity scheduleJob = (SysJobEntity) context.getMergedJobDataMap()
        		.get(SysJobEntity.JOB_PARAM_KEY);

        //获取spring bean
        SysJobLogService scheduleJobLogService = (SysJobLogService) SpringContextUtils.getBean("sysJobLogService");
        
        //数据库保存执行记录
        SysJobLogEntity log = new SysJobLogEntity();
        log.setJobId(scheduleJob.getId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setParams(scheduleJob.getParams());

        //任务开始时间
        long startTime = System.currentTimeMillis();
        
        try {
            //执行任务
        	logger.debug("任务准备执行，任务ID：" + scheduleJob.getId());

			Object target = SpringContextUtils.getBean(scheduleJob.getBeanName());
			assert target != null;
			Method method = target.getClass().getDeclaredMethod("run", String.class);
			method.invoke(target, scheduleJob.getParams());
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			//任务状态    0：成功    1：失败
			log.setStatus(0);
			
			logger.debug("任务执行完毕，任务ID：" + scheduleJob.getId() + "  总共耗时：" + times + "毫秒");
		} catch (Exception e) {
			logger.error("任务执行失败，任务ID：" + scheduleJob.getId(), e);
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			
			//任务状态    0：成功    1：失败
			log.setStatus(1);
			log.setError(StringUtils.substring(e.toString(), 0, 2000));
		}finally {
			assert scheduleJobLogService != null;
			scheduleJobLogService.save(log);
		}
    }

	/**
	 * 判断bean是否有效
	 *
	 * @param beanName beanName
	 * @return 返回信息
	 */
	public static DataResult judgeBean(String beanName) {

		if (org.springframework.util.StringUtils.isEmpty(beanName)) {
			return DataResult.fail("spring bean名称不能为空");
		}

		Object target = SpringContextUtils.getBean(beanName);
		if (target == null) {
			return DataResult.fail("spring bean不存在，请检查");
		}
		Method method;
		try {
			method = target.getClass().getDeclaredMethod("run", String.class);
		} catch (Exception e) {
			return DataResult.fail("spring bean中的run方法不存在，请检查");
		}
		if (method == null) {
			return DataResult.fail("spring bean中的run方法不存在，请检查");
		}

		return DataResult.success();
	}
}
