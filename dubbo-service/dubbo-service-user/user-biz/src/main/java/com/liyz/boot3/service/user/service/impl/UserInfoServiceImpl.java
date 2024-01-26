package com.liyz.boot3.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.boot3.service.user.dao.UserInfoMapper;
import com.liyz.boot3.service.user.model.UserInfoDO;
import com.liyz.boot3.service.user.service.UserInfoService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/19 9:44
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoDO> implements UserInfoService {

    @Override
    @Cacheable(cacheNames = {"userInfo"}, key = "'id:' + #p0", unless = "#result == null")
    public UserInfoDO getById(Serializable id) {
        return super.getById(id);
    }
}
