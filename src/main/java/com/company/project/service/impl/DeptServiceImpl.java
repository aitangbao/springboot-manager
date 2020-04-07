package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.company.project.common.utils.Constant;
import com.company.project.entity.SysDept;
import com.company.project.entity.SysUser;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.mapper.SysDeptMapper;
import com.company.project.service.DeptService;
import com.company.project.service.RedisService;
import com.company.project.service.UserService;
import com.company.project.common.utils.CodeUtil;
import com.company.project.vo.req.DeptAddReqVO;
import com.company.project.vo.req.DeptUpdateReqVO;
import com.company.project.vo.resp.DeptRespNodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DeptServiceImpl implements DeptService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private UserService userService;

    @Override
    public SysDept addDept(DeptAddReqVO vo) {
        System.out.println("fds");
        String relationCode;
        long result = redisService.incrby(Constant.DEPT_CODE_KEY, 1);
        String deptCode = CodeUtil.deptCode(String.valueOf(result), 6, "0");
        SysDept parent = sysDeptMapper.selectById(vo.getPid());
        if (vo.getPid().equals("0")) {
            relationCode = deptCode;
        } else if (null == parent) {
            log.error("传入的 pid:{}不合法", vo.getPid());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        } else {
            relationCode = parent.getRelationCode() + deptCode;
        }
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(vo, sysDept);
        sysDept.setCreateTime(new Date());
        sysDept.setDeptNo(deptCode);
        sysDept.setRelationCode(relationCode);
        int count = sysDeptMapper.insert(sysDept);
        if (count != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        return sysDept;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(DeptUpdateReqVO vo) {

        SysDept sysDept = sysDeptMapper.selectById(vo.getId());
        if (null == sysDept) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        SysDept update = new SysDept();
        BeanUtils.copyProperties(vo, update);
        update.setUpdateTime(new Date());
        int count = sysDeptMapper.updateById(update);
        if (count != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }

        /**
         * 说明层级发生了变化
         */
        if (!StringUtils.isEmpty(vo.getPid()) && !vo.getPid().equals(sysDept.getPid())) {
            SysDept parent = sysDeptMapper.selectById(vo.getPid());
            if (!vo.getPid().equals("0") && null == parent) {
                log.error("传入 的 pid:{}不合法", vo.getId());
                throw new BusinessException(BaseResponseCode.DATA_ERROR);
            }
            SysDept oldParent = sysDeptMapper.selectById(sysDept.getPid());
            String oldRelationCode;
            String newRelationCode;
            /**
             * 根目录降到其他目录
             */
            if (sysDept.getPid().equals("0")) {
                oldRelationCode = sysDept.getDeptNo();
                newRelationCode = parent.getRelationCode() + sysDept.getDeptNo();
            } else if (vo.getPid().equals("0")) {//其他目录升级到跟目录
                oldRelationCode = sysDept.getRelationCode();
                newRelationCode = sysDept.getDeptNo();
            } else {
                oldRelationCode = oldParent.getRelationCode();
                newRelationCode = parent.getRelationCode();
            }
            sysDeptMapper.updateRelationCode(oldRelationCode, newRelationCode, sysDept.getRelationCode());
        }
    }

    @Override
    public SysDept detailInfo(String id) {
        return sysDeptMapper.selectById(id);
    }

    @Override
    public void deleted(String id) {
        SysDept sysDept = sysDeptMapper.selectById(id);
        if (null == sysDept) {
            log.error("传入 的 id:{}不合法", id);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        List<String> deptIds = sysDeptMapper.selectChildIds(sysDept.getRelationCode());
        List<SysUser> list = userService.getUserListByDeptIds(deptIds);
        if (!list.isEmpty()) {
            throw new BusinessException(BaseResponseCode.NOT_PERMISSION_DELETED_DEPT);
        }
        sysDept.setDeleted(0);
        sysDept.setUpdateTime(new Date());
        int count = sysDeptMapper.updateById(sysDept);
        if (count != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public List<DeptRespNodeVO> deptTreeList(String deptId) {
        List<SysDept> list;
        if (StringUtils.isEmpty(deptId)) {
            list = sysDeptMapper.selectList(new QueryWrapper<SysDept>());
        } else {
            SysDept sysDept = sysDeptMapper.selectById(deptId);
            if (sysDept == null) {
                throw new BusinessException(BaseResponseCode.DATA_ERROR);
            }
            List<String> childIds = sysDeptMapper.selectChildIds(sysDept.getRelationCode());
            list = sysDeptMapper.selectAllByNotContainChild(childIds);
        }
        //默认加一个顶级方便新增顶级部门
        DeptRespNodeVO respNodeVO = new DeptRespNodeVO();
        respNodeVO.setTitle("默认顶级部门");
        respNodeVO.setId("0");
        respNodeVO.setSpread(true);
        respNodeVO.setChildren(getTree(list));
        List<DeptRespNodeVO> result = new ArrayList<>();
        result.add(respNodeVO);
        return result;
    }

    private List<DeptRespNodeVO> getTree(List<SysDept> all) {
        List<DeptRespNodeVO> list = new ArrayList<>();
        for (SysDept sysDept : all) {
            if (sysDept.getPid().equals("0")) {
                DeptRespNodeVO deptTree = new DeptRespNodeVO();
                BeanUtils.copyProperties(sysDept, deptTree);
                deptTree.setTitle(sysDept.getName());
                deptTree.setSpread(true);
                deptTree.setChildren(getChild(sysDept.getId(), all));
                list.add(deptTree);
            }
        }
        return list;
    }

    private List<DeptRespNodeVO> getChild(String id, List<SysDept> all) {
        List<DeptRespNodeVO> list = new ArrayList<>();
        for (SysDept sysDept : all) {
            if (sysDept.getPid().equals(id)) {
                DeptRespNodeVO deptTree = new DeptRespNodeVO();
                BeanUtils.copyProperties(sysDept, deptTree);
                deptTree.setTitle(sysDept.getName());
                deptTree.setChildren(getChild(sysDept.getId(), all));
                list.add(deptTree);
            }
        }
        return list;
    }

    @Override
    public List<SysDept> selectAll() {
        List<SysDept> list = sysDeptMapper.selectList(new QueryWrapper<>());
        for (SysDept sysDept : list) {
            SysDept parent = sysDeptMapper.selectById(sysDept.getPid());
            if (parent != null) {
                sysDept.setPidName(parent.getName());
            }
        }
        return list;
    }
}
