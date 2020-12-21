package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SysDept;
import com.company.project.vo.resp.DeptRespNodeVO;

import java.util.List;

/**
 * 部门
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface DeptService extends IService<SysDept> {

    /**
     * 添加部门
     *
     * @param vo vo
     */
    void addDept(SysDept vo);

    /**
     * 更新部门
     *
     * @param vo vo
     */
    void updateDept(SysDept vo);

    /**
     * 删除部门
     *
     * @param id id
     */
    void deleted(String id);

    /**
     * 部门树形列表
     *
     * @param deptId   deptId
     * @param disabled 最顶级是否可用
     * @return 树形列表
     */
    List<DeptRespNodeVO> deptTreeList(String deptId, Boolean disabled);
}
