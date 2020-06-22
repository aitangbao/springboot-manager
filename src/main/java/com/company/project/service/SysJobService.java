package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SysJobEntity;

import java.util.List;

/**
 * 定时任务 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface SysJobService extends IService<SysJobEntity> {

    void saveJob(SysJobEntity sysJob);

    void updateJobById(SysJobEntity sysJob);

    void delete(List<String> ids);

    void run(List<String> ids);

    void pause(List<String> ids);

    void resume(List<String> ids);

    void updateBatch(List<String> ids, int status);
}

