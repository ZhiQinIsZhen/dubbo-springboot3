package com.liyz.boot3.service.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.boot3.service.auth.dao.AuthJwtMapper;
import com.liyz.boot3.service.auth.model.AuthJwtDO;
import com.liyz.boot3.service.auth.service.AuthJwtService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 17:59
 */
@Service
public class AuthJwtServiceImpl extends ServiceImpl<AuthJwtMapper, AuthJwtDO> implements AuthJwtService {

    /**
     * 根据资源ID获取JWT配置信息
     *
     * @param clientId 资源ID
     * @return JWT配置信息
     */
    @Override
    @Cacheable(cacheNames = {"test"}, key = "'authJwt:clientId:' + #clientId", unless = "#result == null")
    public AuthJwtDO getByClientId(String clientId) {
        return getOne(Wrappers.lambdaQuery(AuthJwtDO.class).eq(AuthJwtDO::getClientId, clientId));
    }
}
