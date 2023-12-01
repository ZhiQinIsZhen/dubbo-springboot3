package com.liyz.boot3.service.user.remote;

import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.service.user.bo.UserLogoutLogBO;
import jakarta.validation.constraints.NotNull;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:21
 */
public interface RemoteUserLogoutLogService {

    /**
     * 根据userId分页查询登出日志
     *
     * @param userId 用户ID
     * @param pageBO 分页参数
     * @return 用户登出日志
     */
    RemotePage<UserLogoutLogBO> page(@NotNull Long userId, @NotNull PageBO pageBO);
}
