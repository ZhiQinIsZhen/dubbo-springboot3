package com.liyz.boot3.service.staff.remote;

import com.liyz.boot3.service.staff.bo.StaffRoleBO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/13 13:40
 */
public interface RemoteStaffRoleService {

    /**
     * 给员工绑定一个角色
     *
     * @param staffRoleBO 员工角色参数
     * @return 员工角色
     */
    StaffRoleBO bindRole(@NotNull StaffRoleBO staffRoleBO);

    /**
     * 查询员工拥有的角色
     *
     * @param staffId 员工ID
     * @return 员工角色
     */
    List<StaffRoleBO> listByStaffId(@NotNull Long staffId);
}
