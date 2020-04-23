package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SysJobEntity;

import java.util.List;

/**
 * 定时任务
 *
 * @author manager
 * @email *****@mail.com
 * @date 2020-04-22 14:23:36
 */
public interface SysJobService extends IService<SysJobEntity> {

    void saveJob(SysJobEntity sysJob);

    void updateJobById(SysJobEntity sysJob);

    void delete(List<String> ids);

    void run(List<String> ids);

    void pause(List<String> ids);

    void resume(List<String> ids);

    int updateBatch(List<String> ids, int status);
}

