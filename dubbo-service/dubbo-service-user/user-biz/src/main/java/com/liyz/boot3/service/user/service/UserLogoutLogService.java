package com.liyz.boot3.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liyz.boot3.service.auth.enums.Device;
import com.liyz.boot3.service.user.model.UserLogoutLogDO;

import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/19 9:42
 */
public interface UserLogoutLogService extends IService<UserLogoutLogDO> {

    /**
     * 获取上次登出时间
     *
     * @param userId 员工ID
     * @return 上次登出时间
     */
    Date lastLogoutTime(Long userId, Device device);
}
