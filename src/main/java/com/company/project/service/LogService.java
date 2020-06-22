package com.company.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.company.project.entity.SysLog;

import java.util.List;

/**
 * 系统日志
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface LogService {

    IPage<SysLog> pageInfo(SysLog vo);

    void deleted(List<String> logIds);
}
