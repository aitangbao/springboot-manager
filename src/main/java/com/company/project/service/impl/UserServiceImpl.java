package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.entity.SysDept;
import com.company.project.entity.SysRole;
import com.company.project.entity.SysUser;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.mapper.SysDeptMapper;
import com.company.project.mapper.SysUserMapper;
import com.company.project.service.*;
import com.company.project.vo.req.*;
import com.company.project.common.utils.PasswordUtils;
import com.company.project.vo.resp.LoginRespVO;
import com.company.project.vo.resp.UserOwnRoleRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private HttpSessionService httpSessionService;

    @Value("${spring.redis.allowMultipleLogin}")
    private Boolean allowMultipleLogin;
    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public String register(RegisterReqVO vo) {

        SysUser sysUserOne = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, vo.getUsername()));
        if (sysUserOne != null) {
            throw new BusinessException("用户名已存在！");
        }

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(vo, sysUser);
        sysUser.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(vo.getPassword(), sysUser.getSalt());
        sysUser.setPassword(encode);
        int i = sysUserMapper.insert(sysUser);
        if (i != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        return sysUser.getId();
    }

    @Override
    public LoginRespVO login(LoginReqVO vo) {
        SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, vo.getUsername()));
        if (null == sysUser) {
            throw new BusinessException(BaseResponseCode.NOT_ACCOUNT);
        }
        if (sysUser.getStatus() == 2) {
            throw new BusinessException(BaseResponseCode.USER_LOCK);
        }
        if (!PasswordUtils.matches(sysUser.getSalt(), vo.getPassword(), sysUser.getPassword())) {
            throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
        }
        LoginRespVO respVO = new LoginRespVO();
        BeanUtils.copyProperties(sysUser, respVO);

        //是否删除之前token， 此处控制是否支持多登陆端；
        // true:允许多处登陆; false:只能单处登陆，顶掉之前登陆
        if (!allowMultipleLogin) {
            httpSessionService.abortUserById(sysUser.getId());
        }

        String token = httpSessionService.createTokenAndUser(sysUser, getRolesByUserId(sysUser.getId()), getPermissionsByUserId(sysUser.getId()));
        respVO.setAccessToken(token);

        return respVO;
    }

    private List<String> getRolesByUserId(String userId) {
        return roleService.getRoleNames(userId);
    }

    private Set<String> getPermissionsByUserId(String userId) {
        return permissionService.getPermissionsByUserId(userId);
    }


    @Override
    public void updateUserInfo(SysUser vo, String operationId) {


        SysUser sysUser = sysUserMapper.selectById(vo.getId());
        if (null == sysUser) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        //如果用户名变更
        if (!sysUser.getUsername().equals(vo.getUsername())) {
            SysUser sysUserOne = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, vo.getUsername()));
            if (sysUserOne != null) {
                throw new BusinessException("用户名已存在！");
            }
        }

        //如果用户名、密码、状态 变更，删除redis中用户绑定的角色跟权限
        if (!sysUser.getUsername().equals(vo.getUsername())
                || (!StringUtils.isEmpty(vo.getPassword())
                && !sysUser.getPassword().equals(PasswordUtils.encode(vo.getPassword(), sysUser.getSalt())))
                || !sysUser.getStatus().equals(vo.getStatus())) {
            httpSessionService.abortUserById(vo.getId());
        }

        BeanUtils.copyProperties(vo, sysUser);
        if (!StringUtils.isEmpty(vo.getPassword())) {
            String newPassword = PasswordUtils.encode(vo.getPassword(), sysUser.getSalt());
            sysUser.setPassword(newPassword);
        } else {
            sysUser.setPassword(null);
        }
        sysUser.setUpdateId(operationId);
        sysUserMapper.updateById(sysUser);

    }

    @Override
    public void updateUserInfoMy(SysUser vo, String operationId) {


        SysUser sysUser = sysUserMapper.selectById(vo.getId());
        if (null == sysUser) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        BeanUtils.copyProperties(vo, sysUser);
        if (!StringUtils.isEmpty(vo.getPassword())) {
            String newPassword = PasswordUtils.encode(vo.getPassword(), sysUser.getSalt());
            sysUser.setPassword(newPassword);
        } else {
            sysUser.setPassword(null);
        }
        sysUser.setUpdateId(operationId);
        sysUserMapper.updateById(sysUser);

    }

    @Override
    public IPage<SysUser> pageInfo(SysUser vo) {
        Page page = new Page(vo.getPage(), vo.getLimit());
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(vo.getUsername())) {
            queryWrapper.like(SysUser::getUsername, vo.getUsername());
        }
        if (!StringUtils.isEmpty(vo.getStartTime())) {
            queryWrapper.gt(SysUser::getCreateTime, vo.getStartTime());
        }
        if (!StringUtils.isEmpty(vo.getEndTime())) {
            queryWrapper.lt(SysUser::getCreateTime, vo.getEndTime());
        }
        if (!StringUtils.isEmpty(vo.getNickName())) {
            queryWrapper.like(SysUser::getNickName, vo.getNickName());
        }
        if (!StringUtils.isEmpty(vo.getStatus())) {
            queryWrapper.eq(SysUser::getStatus, vo.getStatus());
        }
        if (!StringUtils.isEmpty(vo.getDeptNo())) {
            LambdaQueryWrapper<SysDept> queryWrapperDept = Wrappers.lambdaQuery();
            queryWrapperDept.select(SysDept::getId).like(SysDept::getRelationCode, vo.getDeptNo());
            List<Object> list = sysDeptMapper.selectObjs(queryWrapperDept);
            queryWrapper.in(SysUser::getDeptId, list);
        }
        queryWrapper.orderByDesc(SysUser::getCreateTime);
        IPage<SysUser> iPage = sysUserMapper.selectPage(page, queryWrapper);
        if (!iPage.getRecords().isEmpty()) {
            for (SysUser sysUser : iPage.getRecords()) {
                SysDept sysDept = sysDeptMapper.selectById(sysUser.getDeptId());
                if (sysDept != null) {
                    sysUser.setDeptName(sysDept.getName());
                }
            }
        }
        return iPage;
    }

    @Override
    public void addUser(SysUser vo) {

        SysUser sysUserOne = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, vo.getUsername()));
        if (sysUserOne != null) {
            throw new BusinessException("用户已存在，请勿重复添加！");
        }

        vo.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(vo.getPassword(), vo.getSalt());
        vo.setPassword(encode);
        vo.setStatus(1);
        vo.setCreateWhere(1);
        int i = sysUserMapper.insert(vo);
        if (i != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        if (null != vo.getRoleIds() && !vo.getRoleIds().isEmpty()) {
            UserRoleOperationReqVO reqVO = new UserRoleOperationReqVO();
            reqVO.setUserId(vo.getId());
            reqVO.setRoleIds(vo.getRoleIds());
            userRoleService.addUserRoleInfo(reqVO);
        }
    }

    @Override
    public void logout() {
        httpSessionService.abortUserByToken();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @Override
    public void updatePwd(UpdatePasswordReqVO vo, String userId) {

        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        if ("test".equals(env) && "guest".equals(sysUser.getUsername())) {
            throw new BusinessException("演示环境禁止修改演示账号密码");
        }

        if (!PasswordUtils.matches(sysUser.getSalt(), vo.getOldPwd(), sysUser.getPassword())) {
            throw new BusinessException(BaseResponseCode.OLD_PASSWORD_ERROR);
        }
        if (sysUser.getPassword().equals(PasswordUtils.encode(vo.getNewPwd(), sysUser.getSalt()))) {
            throw new BusinessException("新密码不能与旧密码相同");
        }
        sysUser.setPassword(PasswordUtils.encode(vo.getNewPwd(), sysUser.getSalt()));
        int i = sysUserMapper.updateById(sysUser);
        if (i != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        httpSessionService.abortAllUserByToken();

    }

    @Override
    public List<SysUser> getUserListByDeptIds(List<Object> deptIds) {
        return sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().in(SysUser::getDeptId, deptIds));
    }

    @Override
    public UserOwnRoleRespVO getUserOwnRole(String userId) {
        List<String> roleIdsByUserId = userRoleService.getRoleIdsByUserId(userId);
        List<SysRole> list = roleService.selectAllRoles();
        UserOwnRoleRespVO vo = new UserOwnRoleRespVO();
        vo.setAllRole(list);
        vo.setOwnRoles(roleIdsByUserId);
        return vo;
    }
}
