package com.liyz.boot3.service.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.boot3.service.auth.enums.LoginType;
import com.liyz.boot3.service.user.dao.UserAuthMobileMapper;
import com.liyz.boot3.service.user.model.UserAuthMobileDO;
import com.liyz.boot3.service.user.model.base.UserAuthBaseDO;
import com.liyz.boot3.service.user.service.UserAuthMobileService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/19 9:45
 */
@Service
public class UserAuthMobileServiceImpl extends ServiceImpl<UserAuthMobileMapper, UserAuthMobileDO> implements UserAuthMobileService {

    /**
     * 获取认证信息
     *
     * @param username 用户名
     * @return 认证信息
     */
    @Override
    @Cacheable(cacheNames = {"userInfo"}, key = "'mobile:' + #username", unless = "#result == null")
    public UserAuthBaseDO getByUsername(String username) {
        return getOne(Wrappers.lambdaQuery(UserAuthMobileDO.builder().mobile(username).build()));
    }

    @Override
    @CacheEvict(cacheNames = {"userInfo"}, key = "'mobile:' + #entity.mobile")
    public boolean save(UserAuthMobileDO entity) {
        return super.save(entity);
    }

    @Override
    @Cacheable(cacheNames = {"userInfo"}, key = "'mobile:id:' + #id", unless = "#result == null")
    public UserAuthMobileDO getById(Serializable id) {
        return super.getById(id);
    }

    /**
     * 获取登录方式
     *
     * @return 登录方式
     */
    @Override
    public LoginType loginType() {
        return LoginType.MOBILE;
    }
}
