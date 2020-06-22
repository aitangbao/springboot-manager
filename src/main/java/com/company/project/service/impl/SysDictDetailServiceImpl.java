package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.common.exception.BusinessException;
import com.company.project.entity.SysDictEntity;
import com.company.project.mapper.SysDictDetailMapper;
import com.company.project.mapper.SysDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.entity.SysDictDetailEntity;
import com.company.project.service.SysDictDetailService;
import org.springframework.util.CollectionUtils;

/**
 * 数据字典 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service("sysDictDetailService")
public class SysDictDetailServiceImpl extends ServiceImpl<SysDictDetailMapper, SysDictDetailEntity> implements SysDictDetailService {

    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;

    @Autowired
    private SysDictMapper sysDictMapper;


    @Override
    public IPage<SysDictDetailEntity> listByPage(Page page, String dictId) {

        SysDictEntity sysDictEntity = sysDictMapper.selectById(dictId);
        if (sysDictEntity == null) {
            throw new BusinessException("获取字典数据失败!");
        }

        LambdaQueryWrapper<SysDictDetailEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictDetailEntity::getDictId, dictId);
        wrapper.orderByAsc(SysDictDetailEntity::getSort);
        IPage<SysDictDetailEntity> result = sysDictDetailMapper.selectPage(page, wrapper);
        if (!CollectionUtils.isEmpty(result.getRecords())) {
            result.getRecords().parallelStream().forEach(entity -> {
                entity.setDictName(sysDictEntity.getName());
            });
        }
        return result;
    }
}