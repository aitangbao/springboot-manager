package com.company.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.company.project.entity.SysLog;

import java.util.List;

public interface LogService {

    IPage<SysLog> pageInfo(SysLog vo);

    void deleted(List<String> logIds);
}
