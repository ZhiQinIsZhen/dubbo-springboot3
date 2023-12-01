package com.liyz.boot3.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.boot3.service.auth.enums.Device;
import com.liyz.boot3.service.user.dao.UserLogoutLogMapper;
import com.liyz.boot3.service.user.model.UserLogoutLogDO;
import com.liyz.boot3.service.user.service.UserLogoutLogService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/19 9:43
 */
@Service
public class UserLogoutLogServiceImpl extends ServiceImpl<UserLogoutLogMapper, UserLogoutLogDO> implements UserLogoutLogService {


    /**
     * 获取上次登出时间
     *
     * @param userId 员工ID
     * @return 上次登出时间
     */
    @Override
    @Cacheable(cacheNames = {"userInfo"}, key = "'lastLogoutTime:' + #device.type + ':' + #userId", unless = "#result == null")
    public Date lastLogoutTime(Long userId, Device device) {
        UserLogoutLogDO userLogoutLogDO = lambdaQuery()
                .select(UserLogoutLogDO::getLogoutTime)
                .eq(UserLogoutLogDO::getUserId, userId)
                .eq(UserLogoutLogDO::getDevice, device.getType())
                .orderByDesc(UserLogoutLogDO::getId)
                .last(" limit 1")
                .one();
        return Objects.nonNull(userLogoutLogDO) ? userLogoutLogDO.getLogoutTime() : null;
    }

    @Override
    @CacheEvict(cacheNames = {"userInfo"}, key = "'lastLogoutTime:' + #entity.device + ':' + #entity.userId")
    public boolean save(UserLogoutLogDO entity) {
        return super.save(entity);
    }
}
