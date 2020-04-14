package com.company.project.service;

import com.company.project.entity.SysDept;
import com.company.project.vo.resp.DeptRespNodeVO;

import java.util.List;

public interface DeptService {

    SysDept addDept(SysDept vo);

    void updateDept(SysDept vo);

    SysDept detailInfo(String id);

    void deleted(String id);

    List<DeptRespNodeVO> deptTreeList(String deptId);

    List<SysDept> selectAll();
}
