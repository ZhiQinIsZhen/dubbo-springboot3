package com.liyz.boot3.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liyz.boot3.service.auth.enums.Device;
import com.liyz.boot3.service.user.model.UserLoginLogDO;

import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/19 9:43
 */
public interface UserLoginLogService extends IService<UserLoginLogDO> {

    /**
     * 获取上次登录时间
     *
     * @param userId 员工ID
     * @param device 设备类型
     * @return 上次登录时间
     */
    Date lastLoginTime(Long userId, Device device);
}
