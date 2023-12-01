package com.liyz.boot3.service.user.remote;


import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.service.user.bo.UserInfoBO;
import jakarta.validation.constraints.NotNull;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:16
 */
public interface RemoteUserInfoService {

    /**
     * 根据userId获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfoBO getByUserId(@NotNull Long userId);

    /**
     * 分页查询客户信息
     *
     * @param pageBO 分页参数
     * @return 客户信息
     */
    RemotePage<UserInfoBO> page(@NotNull PageBO pageBO);
}
