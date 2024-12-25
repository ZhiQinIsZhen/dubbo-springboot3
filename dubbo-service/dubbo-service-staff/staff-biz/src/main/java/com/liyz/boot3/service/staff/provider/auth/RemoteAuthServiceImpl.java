package com.liyz.boot3.service.staff.provider.auth;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.common.util.DateUtil;
import com.liyz.boot3.common.util.PatternUtil;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.auth.bo.AuthUserLoginBO;
import com.liyz.boot3.service.auth.bo.AuthUserLogoutBO;
import com.liyz.boot3.service.auth.bo.AuthUserRegisterBO;
import com.liyz.boot3.service.auth.enums.LoginType;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
import com.liyz.boot3.service.auth.exception.RemoteAuthServiceException;
import com.liyz.boot3.service.auth.remote.RemoteAuthService;
import com.liyz.boot3.service.staff.model.*;
import com.liyz.boot3.service.staff.model.base.StaffAuthBaseDO;
import com.liyz.boot3.service.staff.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 11:10
 */
@Slf4j
@DubboService(tag = "staff")
public class RemoteAuthServiceImpl implements RemoteAuthService {

    @Resource
    private StaffInfoService staffInfoService;
    @Resource
    private StaffAuthMobileService staffAuthMobileService;
    @Resource
    private StaffAuthEmailService staffAuthEmailService;
    @Resource
    private StaffLoginLogService staffLoginLogService;
    @Resource
    private StaffLogoutLogService staffLogoutLogService;
    @Resource
    private StaffAuthorityService staffAuthorityService;
    @Resource
    private StaffRoleService staffRoleService;
    @Resource
    private SystemRoleAuthorityService systemRoleAuthorityService;
    @Resource
    private SystemAuthorityService systemAuthorityService;

    /**
     * 用户注册
     *
     * @param authUserRegister 注册参数
     * @return True：注册成功；false：注册失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean registry(AuthUserRegisterBO authUserRegister) {
        boolean isEmail = PatternUtil.matchEmail(authUserRegister.getUsername());
        //判断该用户名是否存在
        boolean userNameExist = isEmail ?
                staffAuthEmailService.lambdaQuery().eq(StaffAuthEmailDO::getEmail, authUserRegister.getUsername()).exists() :
                staffAuthMobileService.lambdaQuery().eq(StaffAuthMobileDO::getMobile, authUserRegister.getUsername()).exists();
        if (userNameExist) {
            throw new RemoteAuthServiceException(isEmail ? AuthExceptionCodeEnum.EMAIL_EXIST : AuthExceptionCodeEnum.MOBILE_EXIST);
        }
        StaffInfoDO staffInfoDO = BeanUtil.copyProperties(authUserRegister, StaffInfoDO::new, (s, t) -> {
            if (isEmail) {
                t.setEmail(authUserRegister.getUsername());
            } else {
                t.setMobile(authUserRegister.getUsername());
            }
            t.setRegistryTime(DateUtil.currentDate());
        });
        staffInfoService.save(staffInfoDO);
        if (StringUtils.isNotBlank(staffInfoDO.getMobile())) {
            StaffAuthMobileDO mobileDO = StaffAuthMobileDO.builder().mobile(staffInfoDO.getMobile()).build();
            mobileDO.setStaffId(staffInfoDO.getStaffId());
            mobileDO.setPassword(authUserRegister.getPassword());
            staffAuthMobileService.save(mobileDO);
        }
        if (StringUtils.isNotBlank(staffInfoDO.getEmail())) {
            StaffAuthEmailDO emailDO = StaffAuthEmailDO.builder().email(staffInfoDO.getEmail()).build();
            emailDO.setStaffId(staffInfoDO.getStaffId());
            emailDO.setPassword(authUserRegister.getPassword());
            staffAuthEmailService.save(emailDO);
        }
        return Boolean.TRUE;
    }

    /**
     * 登录
     *
     * @param authUserLogin 登录参数
     * @return 当前登录用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthUserBO login(AuthUserLoginBO authUserLogin) {
        AuthUserBO authUser = AuthUserBO.builder()
                .clientId(authUserLogin.getClientId())
                .username(authUserLogin.getUsername())
                .loginType(authUserLogin.getLoginType())
                .device(authUserLogin.getDevice())
                .authorities(List.of())
                .build();
        Long staffId = this.getStaffId(authUserLogin.getUsername(), authUser);
        if (Objects.isNull(staffId)) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.USER_NOT_EXIST);
        }
        StaffInfoDO staffInfoDO = staffInfoService.getById(staffId);
        if (Objects.isNull(staffInfoDO)) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.USER_NOT_EXIST);
        }
        authUser.setAuthId(staffId);
        authUser.setSalt(staffInfoDO.getSalt());
        //查询角色信息
        List<StaffRoleDO> roles = staffRoleService.list(Wrappers.query(StaffRoleDO.builder().staffId(staffId).build()));
        authUser.setRoleIds(CollectionUtils.isEmpty(roles) ? null : roles.stream().map(StaffRoleDO::getRoleId).collect(Collectors.toList()));
        StaffLoginLogDO staffLoginLogDO = BeanUtil.copyProperties(authUserLogin, StaffLoginLogDO::new, (s, t) -> {
            t.setStaffId(staffId);
            t.setLoginTime(DateUtil.currentDate());
            t.setLoginType(s.getLoginType().getType());
            t.setDevice(s.getDevice().getType());
        });
        staffLoginLogService.save(staffLoginLogDO);
        return authUser;
    }

    /**
     * 登出
     *
     * @param authUserLogout 登出参数
     * @return True：登出成功；false：登出失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean logout(AuthUserLogoutBO authUserLogout) {
        StaffLogoutLogDO staffLogoutLogDO = BeanUtil.copyProperties(authUserLogout, StaffLogoutLogDO::new, (s, t) -> {
            t.setStaffId(s.getAuthId());
            t.setDevice(s.getDevice().getType());
            t.setLogoutTime(DateUtil.currentDate());
        });
        return staffLogoutLogService.save(staffLogoutLogDO);
    }

    /**
     * 获取权限列表
     * todo 父角色功能暂未实现，即只有一层关系
     *
     * @param authUser 认证用户信息
     * @return 权限列表
     */
    @Override
    public List<AuthUserBO.AuthGrantedAuthorityBO> authorities(AuthUserBO authUser) {
        Set<Integer> authorityIdSet = new HashSet<>();
        if (CollectionUtils.isEmpty(authUser.getRoleIds())) {
            //查询角色信息
            List<StaffRoleDO> roles = staffRoleService.list(Wrappers.query(StaffRoleDO.builder().staffId(authUser.getAuthId()).build()));
            if (CollectionUtils.isEmpty(roles)) {
                //这里如果员工没有角色，则直接返回空的权限列表，不再查询临时权限数据
                return RemoteAuthService.super.authorities(authUser);
            }
            authUser.setRoleIds(roles.stream().map(StaffRoleDO::getRoleId).collect(Collectors.toList()));
        }
        List<SystemRoleAuthorityDO> roleAuthorityList = systemRoleAuthorityService.list(Wrappers.lambdaQuery(SystemRoleAuthorityDO.class)
                .in(SystemRoleAuthorityDO::getRoleId, authUser.getRoleIds()));
        if (!CollectionUtils.isEmpty(roleAuthorityList)) {
            roleAuthorityList.forEach(item -> authorityIdSet.add(item.getAuthorityId()));
        }
        //查询临时权限
        List<StaffAuthorityDO> list = staffAuthorityService.list(Wrappers.lambdaQuery(StaffAuthorityDO.class)
                .eq(StaffAuthorityDO::getStaffId, authUser.getAuthId())
                .le(StaffAuthorityDO::getAuthorityEndTime, DateUtil.currentDate()));
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> authorityIdSet.add(item.getAuthorityId()));
        }
        if (CollectionUtils.isEmpty(authorityIdSet)) {
            return RemoteAuthService.super.authorities(authUser);
        }
        //查询权限列表
        List<SystemAuthorityDO> authorityList = systemAuthorityService.list(Wrappers.lambdaQuery(SystemAuthorityDO.class)
                .in(SystemAuthorityDO::getAuthorityId, authorityIdSet));
        return BeanUtil.copyList(authorityList, AuthUserBO.AuthGrantedAuthorityBO::new, (s, t) -> t.setAuthorityCode(s.getAuthority()));
    }

    /**
     * 根据username获取对应用户id
     *
     * @param username 用户名
     * @param authUser 认证用户信息
     * @return 用户ID
     */
    private Long getStaffId(String username, AuthUserBO authUser) {
        LoginType loginType = LoginType.getByType(PatternUtil.checkMobileEmail(username));
        if (Objects.isNull(loginType)) {
            log.warn("username is not email or mobile");
            return null;
        }
        authUser.setLoginType(loginType);
        LoginTypeService loginTypeService = LoginTypeService.LOGIN_TYPE_MAP.get(loginType);
        if (Objects.isNull(loginTypeService)) {
            log.warn("{} can not find LoginTypeService", loginType.name());
            return null;
        }
        StaffAuthBaseDO staffAuthBaseDO = loginTypeService.getByUsername(username);
        if (Objects.isNull(staffAuthBaseDO)) {
            return null;
        }
        authUser.setPassword(staffAuthBaseDO.getPassword());
        return staffAuthBaseDO.getStaffId();
    }
}
