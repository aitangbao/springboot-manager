package com.company.project.mapper;

import com.company.project.entity.SysJobEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 定时任务
 * 
 * @author manager
 * @email *****@mail.com
 * @date 2020-04-22 14:23:36
 */
@Mapper
public interface SysJobMapper extends BaseMapper<SysJobEntity> {

    int updateBatch(Map<String, Object> map);
}
