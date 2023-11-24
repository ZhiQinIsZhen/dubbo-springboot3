package com.liyz.boot3.service.staff.remote;

import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.service.staff.bo.StaffLogoutLogBO;
import jakarta.validation.constraints.NotNull;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:21
 */
public interface RemoteStaffLogoutLogService {

    /**
     * 根据staffId分页查询登出日志
     *
     * @param staffId 员工ID
     * @param pageBO 分页信息
     * @return 登出日志
     */
    RemotePage<StaffLogoutLogBO> page(@NotNull Long staffId, @NotNull PageBO pageBO);
}
