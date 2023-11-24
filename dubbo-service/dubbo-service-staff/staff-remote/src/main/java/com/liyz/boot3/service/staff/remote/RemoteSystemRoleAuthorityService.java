package com.liyz.boot3.service.staff.remote;

import com.liyz.boot3.service.staff.bo.SystemRoleAuthorityBO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 15:35
 */
public interface RemoteSystemRoleAuthorityService {

    /**
     * 给一个权限项绑定一个角色
     *
     * @param systemRoleAuthorityBO 系统角色授权项
     * @return 是否成功
     */
    Boolean bindRole(@NotNull SystemRoleAuthorityBO systemRoleAuthorityBO);

    /**
     * 根据一个角色查询出权限项
     *
     * @param roleId 角色ID
     * @return 系统角色授权列表
     */
    List<SystemRoleAuthorityBO> getByRoleId(@NotNull Integer roleId);
}
