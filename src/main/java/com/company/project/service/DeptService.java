package com.company.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.company.project.entity.SysDept;
import com.company.project.entity.SysUser;
import com.company.project.vo.req.DeptAddReqVO;
import com.company.project.vo.req.DeptPageReqVO;
import com.company.project.vo.req.DeptUpdateReqVO;
import com.company.project.vo.req.UserPageUserByDeptReqVO;
import com.company.project.vo.resp.DeptRespNodeVO;

import java.util.List;

public interface DeptService {

    SysDept addDept(DeptAddReqVO vo);

    void updateDept(DeptUpdateReqVO vo);

    SysDept detailInfo(String id);

    void deleted(String id);

    IPage<SysDept> pageInfo(DeptPageReqVO vo);


    List<DeptRespNodeVO> deptTreeList(String deptId);

    IPage<SysUser> pageDeptUserInfo(UserPageUserByDeptReqVO vo);

    List<SysDept> selectAll();
}
