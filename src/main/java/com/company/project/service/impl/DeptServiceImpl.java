package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.entity.SysDept;
import com.company.project.entity.SysUser;
import com.company.project.mapper.SysDeptMapper;
import com.company.project.mapper.SysUserMapper;
import com.company.project.service.DeptService;
import com.company.project.vo.resp.DeptRespNodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 部门
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
@Slf4j
public class DeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements DeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public void addDept(SysDept vo) {
        String relationCode;
        String deptCode = this.getNewDeptCode();
        SysDept parent = sysDeptMapper.selectById(vo.getPid());
        if ("0".equals(vo.getPid())) {
            relationCode = deptCode;
        } else if (null == parent) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        } else {
            relationCode = parent.getRelationCode() + deptCode;
        }
        vo.setDeptNo(deptCode);
        vo.setRelationCode(relationCode);
        vo.setStatus(1);
        sysDeptMapper.insert(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(SysDept vo) {

        SysDept sysDept = sysDeptMapper.selectById(vo.getId());
        if (null == sysDept) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        sysDeptMapper.updateById(vo);
        //说明层级发生了变化
        if (!StringUtils.isEmpty(vo.getPid()) && !vo.getPid().equals(sysDept.getPid())) {
            SysDept parent = sysDeptMapper.selectById(vo.getPid());
            if (!"0".equals(vo.getPid()) && null == parent) {
                throw new BusinessException(BaseResponseCode.DATA_ERROR);
            }
            SysDept oldParent = sysDeptMapper.selectById(sysDept.getPid());
            String oldRelationCode;
            String newRelationCode;
            //根目录降到其他目录
            if ("0".equals(sysDept.getPid())) {
                oldRelationCode = sysDept.getDeptNo();
                newRelationCode = parent.getRelationCode() + sysDept.getDeptNo();
            } else if ("0".equals(vo.getPid())) { // 其他目录升级到跟目录
                oldRelationCode = sysDept.getRelationCode();
                newRelationCode = sysDept.getDeptNo();
            } else {
                oldRelationCode = oldParent.getRelationCode();
                newRelationCode = parent.getRelationCode();
            }
            LambdaQueryWrapper<SysDept> wrapper = Wrappers.lambdaQuery();
            wrapper.likeLeft(SysDept::getDeptNo, sysDept.getDeptNo());
            List<SysDept> list = sysDeptMapper.selectList(wrapper);
            list.parallelStream().forEach(entity -> {
                String relationCode = entity.getRelationCode().replace(oldRelationCode, newRelationCode);
                entity.setRelationCode(relationCode);
                sysDeptMapper.updateById(entity);
            });
        }
    }

    @Override
    public void deleted(String id) {
        SysDept sysDept = sysDeptMapper.selectById(id);
        if (null == sysDept) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        List<Object> deptIds = sysDeptMapper.selectObjs(Wrappers.<SysDept>lambdaQuery().select(SysDept::getId).likeRight(SysDept::getRelationCode, sysDept.getRelationCode()));
        List<SysUser> list = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().in(SysUser::getDeptId, deptIds));
        if (!CollectionUtils.isEmpty(list)) {
            throw new BusinessException(BaseResponseCode.NOT_PERMISSION_DELETED_DEPT);
        }
        sysDeptMapper.deleteById(id);
    }

    @Override
    public List<DeptRespNodeVO> deptTreeList(String deptId, Boolean disabled) {
        List<SysDept> list;
        if (StringUtils.isEmpty(deptId)) {
            list = sysDeptMapper.selectList(Wrappers.emptyWrapper());
        } else {
            SysDept sysDept = sysDeptMapper.selectById(deptId);
            if (sysDept == null) {
                throw new BusinessException(BaseResponseCode.DATA_ERROR);
            }
            LambdaQueryWrapper<SysDept> queryWrapper = Wrappers.<SysDept>lambdaQuery().likeRight(SysDept::getRelationCode, sysDept.getRelationCode());
            List<Object> childIds = sysDeptMapper.selectObjs(queryWrapper);
            list = sysDeptMapper.selectList(Wrappers.<SysDept>lambdaQuery().notIn(SysDept::getId, childIds));
        }
        // 默认加一个顶级方便新增顶级部门
        DeptRespNodeVO respNodeVO = new DeptRespNodeVO();
        respNodeVO.setTitle("默认顶级部门");
        respNodeVO.setId("0");
        respNodeVO.setSpread(true);
        respNodeVO.setDisabled(disabled);
        respNodeVO.setChildren(getTree(list));
        List<DeptRespNodeVO> result = new ArrayList<>();
        result.add(respNodeVO);
        return result;
    }

    private List<DeptRespNodeVO> getTree(List<SysDept> all) {
        List<DeptRespNodeVO> list = new ArrayList<>();
        for (SysDept sysDept : all) {
            if ("0".equals(sysDept.getPid())) {
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

    //获取新的部门编码
    public String getNewDeptCode() {
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.select(SysDept::getDeptNo);
        //获取所有的deptCode
        List<Object> deptCodes = sysDeptMapper.selectObjs(lambdaQueryWrapper);
        AtomicReference<Integer> maxDeptCode = new AtomicReference<>(0);

        //遍历获取最大的DeptCode
        deptCodes.forEach(o -> {
            String str = String.valueOf(o);
            if (str.length() >= 7) {
                Integer one = Integer.parseInt(str.substring(str.length() - 5));
                if (one > maxDeptCode.get()) {
                    maxDeptCode.set(one);
                }
            }
        });

        return padRight(maxDeptCode.get() + 1, 6, "0");
    }


    /**
     * 右补位，左对齐
     *
     * @param len    目标字符串长度
     * @param alexi  补位字符
     * @param oriStr 原字符串
     * @return 目标字符串
     * 以alexin 做为补位
     */
    public static String padRight(int oriStr, int len, String alexi) {
        StringBuilder str = new StringBuilder();
        int strlen = String.valueOf(oriStr).length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                str.append(alexi);
            }
        }
        str.append(oriStr);
        return "D" + str.toString();
    }
}
