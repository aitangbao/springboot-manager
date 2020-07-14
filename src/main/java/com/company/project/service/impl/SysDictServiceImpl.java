package com.company.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.entity.SysDictDetailEntity;
import com.company.project.mapper.SysDictDetailMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.SysDictMapper;
import com.company.project.entity.SysDictEntity;
import com.company.project.service.SysDictService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDictEntity> implements SysDictService {

    @Resource
    private SysDictDetailMapper sysDictDetailMapper;

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param name 字典名称
     * @return 参数键值
     **/
    public JSONArray getType(String name) {
        if (StringUtils.isEmpty(name)) {
            return new JSONArray();
        }
        //根据名称获取字典
        SysDictEntity dict = this.getOne(Wrappers.<SysDictEntity>lambdaQuery().eq(SysDictEntity::getName, name));
        if (dict == null || dict.getId() == null) {
            return new JSONArray();
        }
        //获取明细
        List<SysDictDetailEntity> list = sysDictDetailMapper.selectList(Wrappers.<SysDictDetailEntity>lambdaQuery().eq(SysDictDetailEntity::getDictId, dict.getId()));
        return JSONArray.parseArray(JSON.toJSONString(list));
    }

}