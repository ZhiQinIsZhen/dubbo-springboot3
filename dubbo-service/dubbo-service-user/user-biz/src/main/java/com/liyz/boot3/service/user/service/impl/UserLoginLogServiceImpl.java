package com.liyz.boot3.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.boot3.service.auth.enums.Device;
import com.liyz.boot3.service.user.dao.UserLoginLogMapper;
import com.liyz.boot3.service.user.model.UserLoginLogDO;
import com.liyz.boot3.service.user.service.UserLoginLogService;
import org.apache.ibatis.session.ResultHandler;
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
 * @date 2023/6/19 9:44
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLogDO> implements UserLoginLogService {

    /**
     * 获取上次登录时间
     *
     * @param userId 员工ID
     * @param device 设备类型
     * @return 上次登录时间
     */
    @Override
    @Cacheable(cacheNames = {"userInfo"}, key = "'lastLoginTime:' + #device.type + ':' + #userId", unless = "#result == null")
    public Date lastLoginTime(Long userId, Device device) {
        UserLoginLogDO userLoginLogDO = lambdaQuery()
                .select(UserLoginLogDO::getLoginTime)
                .eq(UserLoginLogDO::getUserId, userId)
                .eq(UserLoginLogDO::getDevice, device.getType())
                .orderByDesc(UserLoginLogDO::getId)
                .last(" limit 1")
                .one();
        return Objects.nonNull(userLoginLogDO) ? userLoginLogDO.getLoginTime() : null;
    }

    @Override
    @CacheEvict(cacheNames = {"userInfo"}, key = "'lastLoginTime:' + #entity.device + ':' + #entity.userId")
    public boolean save(UserLoginLogDO entity) {
        return super.save(entity);
    }

    /**
     * 流式查询
     *
     * @param page 分页
     * @param queryWrapper 查询条件
     * @param resultHandler 流式结果
     */
    @Override
    public void pageStream(IPage<UserLoginLogDO> page, Wrapper<UserLoginLogDO> queryWrapper, ResultHandler<UserLoginLogDO> resultHandler) {
        getBaseMapper().selectList(page, queryWrapper, resultHandler);
    }
}
