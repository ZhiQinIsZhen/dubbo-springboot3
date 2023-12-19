package com.liyz.boot3.service.user.remote;

import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.service.user.bo.UserLoginLogBO;
import jakarta.validation.constraints.NotNull;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:19
 */
public interface RemoteUserLoginLogService {

    /**
     * 根据userId分页查询登录日志
     *
     * @param userId 用户ID
     * @param pageBO 分页参数
     * @return 用户登录日志
     */
    RemotePage<UserLoginLogBO> page(@NotNull Long userId, @NotNull PageBO pageBO);

    /**
     * 根据userId分页流式查询登录日志
     *
     * @param userId 用户ID
     * @param pageBO 分页参数
     * @return 用户登录日志
     */
    RemotePage<UserLoginLogBO> pageStream(@NotNull Long userId, @NotNull PageBO pageBO);
}
