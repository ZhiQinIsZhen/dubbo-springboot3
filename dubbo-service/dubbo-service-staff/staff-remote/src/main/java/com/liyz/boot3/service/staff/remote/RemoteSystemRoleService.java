package com.liyz.boot3.service.staff.remote;

import com.liyz.boot3.service.staff.bo.SystemRoleBO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 13:21
 */
public interface RemoteSystemRoleService {

    /**
     * 创建一个角色
     *
     * @param systemRoleBO 系统角色参数
     * @return 系统角色
     */
    SystemRoleBO addSystemRole(@NotNull SystemRoleBO systemRoleBO);

    /**
     * 查询角色列表
     *
     * @return 系统角色列表
     */
    List<SystemRoleBO> list();
}
