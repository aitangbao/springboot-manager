package com.company.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.company.project.entity.SysLog;
import com.company.project.vo.req.SysLogPageReqVO;

import java.util.List;

public interface LogService {

    IPage<SysLog> pageInfo(SysLogPageReqVO vo);

    void deleted(List<String> logIds);
}
