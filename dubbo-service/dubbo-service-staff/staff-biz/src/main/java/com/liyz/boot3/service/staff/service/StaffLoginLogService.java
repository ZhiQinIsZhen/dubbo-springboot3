package com.liyz.boot3.service.staff.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liyz.boot3.service.auth.enums.Device;
import com.liyz.boot3.service.staff.model.StaffLoginLogDO;

import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 14:02
 */
public interface StaffLoginLogService extends IService<StaffLoginLogDO> {

    /**
     * 获取上次登录时间
     *
     * @param staffId 员工ID
     * @param device 设备类型
     * @return 上次登录时间
     */
    Date lastLoginTime(Long staffId, Device device);
}
