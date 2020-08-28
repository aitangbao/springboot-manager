package com.company.project.common.job.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.company.project.common.utils.Constant;
import com.company.project.entity.oshi.SystemHardwareInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 系统信息推送定时任务
 *
 * @author wangyihang
 * @version V1.0
 * @date 2020年8月26日
 */
@Slf4j
@Component("systemTask")
public class SystemTask {
	@Resource
	private SimpMessagingTemplate wsTemplate;
	public void run(String params){
		log.info("【推送消息】开始执行：{}", DateUtil.formatDateTime(new Date()));
		// 查询服务器状态
		SystemHardwareInfo server = new SystemHardwareInfo();
		server.copyTo();
		wsTemplate.convertAndSend(Constant.PUSH_SERVER, JSONUtil.toJsonStr(server));
		//发送给指定用户
//        wsTemplate.convertAndSendToUser()
		log.info("【推送消息】执行结束：{}", DateUtil.formatDateTime(new Date()));
	}
}
