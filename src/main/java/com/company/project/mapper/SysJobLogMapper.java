package com.company.project.mapper;

import com.company.project.entity.SysJobLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志、 Mapper
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Mapper
public interface SysJobLogMapper extends BaseMapper<SysJobLogEntity> {
	
}
