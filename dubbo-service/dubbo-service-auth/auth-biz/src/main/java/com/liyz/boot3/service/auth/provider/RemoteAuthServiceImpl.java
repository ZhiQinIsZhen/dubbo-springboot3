package com.liyz.boot3.service.auth.provider;

import com.alibaba.nacos.api.utils.StringUtils;
import com.liyz.boot3.common.util.RandomUtil;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.auth.bo.AuthUserLoginBO;
import com.liyz.boot3.service.auth.bo.AuthUserLogoutBO;
import com.liyz.boot3.service.auth.bo.AuthUserRegisterBO;
import com.liyz.boot3.service.auth.constants.AuthConstants;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
import com.liyz.boot3.service.auth.exception.RemoteAuthServiceException;
import com.liyz.boot3.service.auth.model.AuthJwtDO;
import com.liyz.boot3.service.auth.model.AuthSourceDO;
import com.liyz.boot3.service.auth.remote.RemoteAuthService;
import com.liyz.boot3.service.auth.service.AuthJwtService;
import com.liyz.boot3.service.auth.service.AuthSourceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 9:28
 */
@Slf4j
@DubboService
public class RemoteAuthServiceImpl implements RemoteAuthService {

    private static final String DUBBO_TAG = "dubbo.tag";

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private AuthJwtService authJwtService;
    @Resource
    private AuthSourceService authSourceService;
    @DubboReference
    private RemoteAuthService remoteAuthService;
    @Resource
    private RedissonClient redissonClient;

    /**
     * 用户注册
     *
     * @param authUserRegister 注册参数
     * @return True：注册成功；false：注册失败
     */
    @Override
    public Boolean registry(AuthUserRegisterBO authUserRegister) {
        if (StringUtils.isBlank(authUserRegister.getClientId())) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.LACK_SOURCE_ID);
        }
        AuthSourceDO authSourceDO = authSourceService.getByClientId(authUserRegister.getClientId());
        if (Objects.isNull(authSourceDO)) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.NON_SET_SOURCE_ID);
        }
        authUserRegister.setPassword(passwordEncoder.encode(authUserRegister.getPassword()));
        authUserRegister.setSalt(RandomUtil.randomChars(16));
        RpcContext.getClientAttachment().setAttachment(DUBBO_TAG, authSourceDO.getClientTag());
        return remoteAuthService.registry(authUserRegister);
    }

    /**
     * 登录
     *
     * @param authUserLogin 登录参数
     * @return 当前登录用户信息
     */
    @Override
    public AuthUserBO login(AuthUserLoginBO authUserLogin) {
        final String clientId = authUserLogin.getClientId();
        if (StringUtils.isBlank(clientId)) {
            log.warn("用户登录错误，原因 : clientId is blank");
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        AuthSourceDO authSourceDO = authSourceService.getByClientId(clientId);
        if (Objects.isNull(authSourceDO)) {
            log.warn("查询资源客户端ID失败，原因没有找到对应的配置信息，clientId : {}", clientId);
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        AuthJwtDO authJwtDO = authJwtService.getByClientId(clientId);
        if (Objects.isNull(authJwtDO)) {
            log.error("解析token失败, 没有找到该应用下jwt配置信息，clientId：{}", clientId);
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        RpcContext.getClientAttachment().setAttachment(DUBBO_TAG, authSourceDO.getClientTag());
        AuthUserBO authUserBO = remoteAuthService.login(authUserLogin);
        if (Objects.isNull(authUserBO)) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.USER_NOT_EXIST);
        }
        String loginKey = UUID.randomUUID().toString();
        RSet<String> set = redissonClient.getSet(AuthConstants.getRedisKey(clientId, authUserBO.getAuthId().toString()));
        if (set.isExists() && authJwtDO.getOneOnline()) {
            set.clear();
        }
        set.add(loginKey);
        set.expire(Duration.of(7, ChronoUnit.DAYS));
        authUserBO.setLoginKey(loginKey);
        return authUserBO;
    }

    /**
     * 登出
     *
     * @param authUserLogout 登出参数
     * @return True：登出成功；false：登出失败
     */
    @Override
    public Boolean logout(AuthUserLogoutBO authUserLogout) {
        if (StringUtils.isBlank(authUserLogout.getClientId())) {
            return Boolean.FALSE;
        }
        AuthSourceDO authSourceDO = authSourceService.getByClientId(authUserLogout.getClientId());
        if (Objects.isNull(authSourceDO)) {
            return Boolean.FALSE;
        }
        RSet<String> set = redissonClient.getSet(AuthConstants.getRedisKey(authSourceDO.getClientId(), authUserLogout.getAuthId().toString()));
        if (set.isExists()) {
            set.remove(authUserLogout.getLoginKey());
        }
        RpcContext.getClientAttachment().setAttachment(DUBBO_TAG, authSourceDO.getClientTag());
        return remoteAuthService.logout(authUserLogout);
    }

    /**
     * 获取权限列表
     *
     * @param authUser 认证用户信息
     * @return 权限列表
     */
    @Override
    public List<AuthUserBO.AuthGrantedAuthorityBO> authorities(AuthUserBO authUser) {
        if (StringUtils.isBlank(authUser.getClientId())) {
            log.warn("获取权限错误，原因 : client is blank");
            return RemoteAuthService.super.authorities(authUser);
        }
        AuthSourceDO authSourceDO = authSourceService.getByClientId(authUser.getClientId());
        if (Objects.isNull(authSourceDO)) {
            return RemoteAuthService.super.authorities(authUser);
        }
        RpcContext.getClientAttachment().setAttachment(DUBBO_TAG, authSourceDO.getClientTag());
        return remoteAuthService.authorities(authUser);
    }


}
