package com.liyz.boot3.service.staff.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.boot3.service.auth.enums.Device;
import com.liyz.boot3.service.staff.dao.StaffLoginLogMapper;
import com.liyz.boot3.service.staff.model.StaffLoginLogDO;
import com.liyz.boot3.service.staff.service.StaffLoginLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 14:02
 */
@Service
public class StaffLoginLogServiceImpl extends ServiceImpl<StaffLoginLogMapper, StaffLoginLogDO> implements StaffLoginLogService {

    /**
     * 获取上次登录时间
     *
     * @param staffId 员工ID
     * @param device 设备类型
     * @return 上次登录时间
     */
    @Override
    public Date lastLoginTime(Long staffId, Device device) {
        Page<StaffLoginLogDO> page = page(
                new Page<>(1, 1),
                Wrappers.lambdaQuery(StaffLoginLogDO.builder().staffId(staffId).device(device.getType()).build()).orderByDesc(StaffLoginLogDO::getId)
        );
        Date lastLoginTime = null;
        if (Objects.nonNull(page) && !CollectionUtils.isEmpty(page.getRecords())) {
            lastLoginTime = page.getRecords().get(0).getLoginTime();
        }
        return lastLoginTime;
    }
}
