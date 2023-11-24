package com.liyz.boot3.service.staff.remote;

import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.service.staff.bo.StaffInfoBO;
import jakarta.validation.constraints.NotNull;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:16
 */
public interface RemoteStaffInfoService {

    /**
     * 根据staffId获取用户信息
     *
     * @param staffId 员工ID
     * @return 员工信息
     */
    StaffInfoBO getByStaffId(@NotNull Long staffId);

    /**
     * 分页查询员工信息
     *
     * @param pageBO 分页信息
     * @return 员工信息
     */
    RemotePage<StaffInfoBO> page(@NotNull PageBO pageBO);
}
