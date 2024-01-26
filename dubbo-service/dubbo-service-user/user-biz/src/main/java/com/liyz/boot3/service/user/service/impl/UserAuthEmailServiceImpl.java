package com.liyz.boot3.service.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.boot3.service.auth.enums.LoginType;
import com.liyz.boot3.service.user.dao.UserAuthEmailMapper;
import com.liyz.boot3.service.user.model.UserAuthEmailDO;
import com.liyz.boot3.service.user.model.base.UserAuthBaseDO;
import com.liyz.boot3.service.user.service.UserAuthEmailService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/19 9:46
 */
@Service
public class UserAuthEmailServiceImpl extends ServiceImpl<UserAuthEmailMapper, UserAuthEmailDO> implements UserAuthEmailService {

    /**
     * 获取认证信息
     *
     * @param username 用户名
     * @return 认证信息
     */
    @Override
    @Cacheable(cacheNames = {"userInfo"}, key = "'email:' + #p0", unless = "#result == null")
    public UserAuthBaseDO getByUsername(String username) {
        return getOne(Wrappers.lambdaQuery(UserAuthEmailDO.builder().email(username).build()));
    }

    @Override
    @CacheEvict(cacheNames = {"userInfo"}, key = "'email:' + #p0.email")
    public boolean save(UserAuthEmailDO entity) {
        return super.save(entity);
    }

    @Override
    @Cacheable(cacheNames = {"userInfo"}, key = "'email:id:' + #p0", unless = "#result == null")
    public UserAuthEmailDO getById(Serializable id) {
        return super.getById(id);
    }

    /**
     * 获取登录方式
     *
     * @return 登录方式
     */
    @Override
    public LoginType loginType() {
        return LoginType.EMAIL;
    }
}
